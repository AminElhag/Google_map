package sd.lemon.googlemap.presentation.app.di

import dagger.Component
import retrofit2.Retrofit
import sd.lemon.googlemap.presentation.app.App
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: App)
    fun retrofit(): Retrofit
}