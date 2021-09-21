package sd.lemon.googlemap.presentation.map

import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sd.lemon.googlemap.domain.MapUseCase

class MapPresenter(
    private val view: MapView,
    private val mapUseCase: MapUseCase
) {

    private var firstPoint: LatLng? = null
    private var secondPoint: LatLng? = null

    private val compositeDisposable = CompositeDisposable()


    fun setPoint(latLng: LatLng) {
        if (firstPoint == null) {
            firstPoint = latLng
            view.clear()
//            view.addMark(latLng)
            return
        }
        if (secondPoint == null) {
            secondPoint = latLng
            doWork()
        }
    }

    // 101.100,10.100
    private fun doWork() {
        view.onWaiting()
        val subscribe = mapUseCase.execute(
            MapUseCase.Parameters(
                pointOne = "${firstPoint!!.latitude},${firstPoint!!.longitude}",
                pointTwo = "${secondPoint!!.latitude},${secondPoint!!.longitude}"
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onSuccess(it)
            }, {
                view.onError(it)
            })

        compositeDisposable.add(subscribe)
        firstPoint = null
        secondPoint = null
    }


    /*fun takeLatLng(latLng: LatLng) {
        if (index < 2) {
            point.add(index, latLng.toString())
            view.addMark(latLng)
            index++
            ifTwoPointIsSet()
        } else if (index >= 2) {
            view.clear()
            index = 0
        }
    }

    private fun ifTwoPointIsSet() {
        if (point.size == 2) {
            view.onWaiting()
            val subscribe = mapUseCase.execute(MapUseCase.Parameters(point[0], point[1]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccess(it)
                }, {
                    view.onError(it)
                })

            compositeDisposable.add(subscribe)
        }
    }
*/
    fun onDestroy() {
        compositeDisposable.clear()
    }
}