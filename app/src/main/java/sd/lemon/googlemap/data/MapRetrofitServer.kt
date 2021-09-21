package sd.lemon.googlemap.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sd.lemon.googlemap.domain.models.MapResponse

interface MapRetrofitServer {
    //1/route?point=49.932707,11.588051&point=50.3404,11.64705&vehicle=car&key=3e4f56dc-3f27-40da-9666-653c2c8ee896&type=json
    //1/route?point={lat},{lng}&point={pointLat},{pointLng}&vehicle={vehicle}&key=3e4f56dc-3f27-40da-9666-653c2c8ee896&type=json
    @GET("1/route")
    fun getMap(
        @Query("point") pointOne: String,
        @Query("point") pointTwo: String,
        @Query("vehicle") vehicle: String = "car",
        @Query("key") key: String = "3e4f56dc-3f27-40da-9666-653c2c8ee896",
        @Query("type") type: String = "json"
    ): Observable<MapResponse>
}