package sd.lemon.googlemap.domain.common

import io.reactivex.Observable

interface UseCase<P : UseCase.Parameters?, R> {
    fun execute(parameters: P): Observable<R>?
    interface Parameters
}