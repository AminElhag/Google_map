package sd.lemon.googlemap.presentation.map.di

import dagger.Component
import sd.lemon.googlemap.presentation.app.di.AppComponent
import sd.lemon.googlemap.presentation.app.di.PerActivity
import sd.lemon.googlemap.presentation.map.MapsActivity

@Component(modules = [MapModule::class], dependencies = [AppComponent::class])
@PerActivity
interface MapComponent {
    fun inject(mapsActivity: MapsActivity)
}