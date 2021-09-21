package sd.lemon.googlemap.presentation.app

import android.app.Application
import sd.lemon.googlemap.presentation.app.di.AppComponent
import sd.lemon.googlemap.presentation.app.di.DaggerAppComponent
import sd.lemon.googlemap.presentation.app.di.RetrofitModule
import javax.inject.Inject

class App : Application() {
    @Inject
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .build()
            .inject(this)
    }
}