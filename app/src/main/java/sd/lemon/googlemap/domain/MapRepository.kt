package sd.lemon.googlemap.domain

import io.reactivex.Observable
import sd.lemon.googlemap.domain.models.MapResponse

interface MapRepository {
fun maping(parameters:MapUseCase.Parameters):Observable<MapResponse>
}