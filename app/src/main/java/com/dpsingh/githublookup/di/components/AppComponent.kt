package com.dpsingh.githublookup.di.components

import com.dpsingh.githublookup.App
import com.dpsingh.githublookup.di.modules.ActivityBuildersModule
import com.dpsingh.githublookup.di.modules.ActivityBuildersModule_ContributeMainActivity
import com.dpsingh.githublookup.di.modules.AppModule
import com.dpsingh.githublookup.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    ActivityBuildersModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}