package sd.lemon.googlemap.presentation.map

import com.google.android.gms.maps.model.LatLng
import sd.lemon.googlemap.domain.models.MapResponse

interface MapView {
    fun addMark(latLng: LatLng)
    fun clear()
    fun onSuccess(mapResponse: MapResponse)
    fun onError(throwable: Throwable)
    fun onWaiting()
}