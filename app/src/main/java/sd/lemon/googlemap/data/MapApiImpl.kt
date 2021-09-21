package sd.lemon.googlemap.data

import io.reactivex.Observable
import sd.lemon.googlemap.domain.MapRepository
import sd.lemon.googlemap.domain.MapUseCase
import sd.lemon.googlemap.domain.models.MapResponse

class MapApiImpl(private val mapRetrofitServer: MapRetrofitServer) : MapRepository {
    override fun maping(parameters: MapUseCase.Parameters): Observable<MapResponse> {
        return mapRetrofitServer.getMap(parameters.pointOne, parameters.pointTwo)
    }
}