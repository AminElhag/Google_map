package sd.lemon.googlemap.presentation.map

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil
import sd.lemon.googlemap.R
import sd.lemon.googlemap.databinding.ActivityMapsBinding
import sd.lemon.googlemap.domain.models.MapResponse
import sd.lemon.googlemap.presentation.app.App
import sd.lemon.googlemap.presentation.map.di.DaggerMapComponent
import sd.lemon.googlemap.presentation.map.di.MapModule
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapView {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var presenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this@MapsActivity)

        DaggerMapComponent.builder()
            .mapModule(MapModule(this))
            .appComponent((application as App).appComponent)
            .build()
            .inject(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == 10) {
            if (permissions[0] == android.Manifest.permission.ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Nice .....",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                finish()
            }
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker
        val latLng = LatLng(15.508457, 32.522854)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

        val result = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (result == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                10
            )
        }

        mMap.setOnMapClickListener {
            presenter.setPoint(it)

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 12F))

        }
    }

    override fun addMark(latLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(latLng).title("Point"))
    }

    override fun clear() {
        mMap.clear()
    }

    override fun onSuccess(mapResponse: MapResponse) {
        binding.progressBar.visibility = View.INVISIBLE
        val path = mapResponse.paths[0]

        val points =
            PolyUtil.decode(path.points)

        mMap.addPolyline(
            PolylineOptions()
                .addAll(points)
        )

        mMap.addMarker(MarkerOptions().position(points.first()))
        mMap.addMarker(MarkerOptions().position(points.last()))

        val bBox = path.BBox
        val topLeft = LatLng(bBox[0], bBox[1])
        val bottomRight = LatLng(bBox[2], bBox[0])
        val topRight = LatLng(topLeft.latitude, bottomRight.longitude)
        val bottomLeft = LatLng(bottomRight.latitude, topLeft.longitude)
        /*     mMap.addPolygon(
                 PolygonOptions()
                     .add(topLeft, topRight, bottomRight, bottomLeft)
                     .strokeColor(Color.RED)
                     .strokeWidth(8F)
             )
        */
        /*mMap.addPolyline(
            PolylineOptions().add(LatLng(bBox[1], bBox[0]), LatLng(bBox[3], bBox[0])).width(10f)
                .color(Color.RED)
        )
        mMap.addPolyline(
            PolylineOptions().add(LatLng(bBox[3], bBox[0]), LatLng(bBox[3], bBox[2])).width(10f)
                .color(Color.GREEN)
        )
        mMap.addPolyline(
            PolylineOptions().add(LatLng(bBox[1], bBox[2]), LatLng(bBox[3], bBox[2])).width(10f)
                .color(Color.BLUE)
        )
        mMap.addPolyline(
            PolylineOptions().add(LatLng(bBox[1], bBox[0]), LatLng(bBox[1], bBox[2])).width(10f)
                .color(Color.YELLOW)
        )*/

        mMap.addPolygon(
            PolygonOptions().add(
                LatLng(bBox[1], bBox[0]),
                LatLng(bBox[3], bBox[0]),
                LatLng(bBox[3], bBox[2]),
                LatLng(bBox[1], bBox[2]),
                LatLng(bBox[1], bBox[0]),
            )
        )
        progressDialog.dismiss()
    }

    override fun onError(throwable: Throwable) {
        Snackbar.make(findViewById(android.R.id.content), "Error:$throwable", Snackbar.LENGTH_LONG)
            .show()
        progressDialog.dismiss()
    }

    override fun onWaiting() {
        progressDialog.setTitle("Wait...")
        progressDialog.setMessage("Map is loading, please wait")
        progressDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}