package sd.lemon.googlemap.presentation.map.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import sd.lemon.googlemap.data.MapApiImpl
import sd.lemon.googlemap.data.MapRetrofitServer
import sd.lemon.googlemap.domain.MapRepository
import sd.lemon.googlemap.domain.MapUseCase
import sd.lemon.googlemap.presentation.app.di.PerActivity
import sd.lemon.googlemap.presentation.map.MapPresenter
import sd.lemon.googlemap.presentation.map.MapView

@Module
class MapModule(private val view: MapView) {


    @Provides
    @PerActivity
    fun providesMapRetrofitServer(retrofit: Retrofit): MapRetrofitServer {
        return retrofit.create(MapRetrofitServer::class.java)
    }

    @Provides
    @PerActivity
    fun providesMapRepository(mapRetrofitServer: MapRetrofitServer): MapRepository {
        return MapApiImpl(mapRetrofitServer)
    }

    @Provides
    @PerActivity
    fun providesMapUseCase(mapRepository: MapRepository): MapUseCase {
        return MapUseCase(mapRepository)
    }

    @Provides
    @PerActivity
    fun providesMapPresenter(mapUseCase: MapUseCase): MapPresenter {
        return MapPresenter(view, mapUseCase)
    }
}