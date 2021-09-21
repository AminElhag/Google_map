package sd.lemon.googlemap.domain

import io.reactivex.Observable
import sd.lemon.googlemap.domain.common.UseCase
import sd.lemon.googlemap.domain.models.MapResponse

class MapUseCase(private val mapRepository: MapRepository) :
    UseCase<MapUseCase.Parameters, MapResponse> {
    override fun execute(parameters: Parameters): Observable<MapResponse> {
        return mapRepository.maping(parameters)
    }

    class Parameters(
        val pointOne: String,
        val pointTwo: String,
    ) : UseCase.Parameters
}