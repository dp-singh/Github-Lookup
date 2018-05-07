package com.dpsingh.githublookup

import com.dpsingh.githublookup.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        if (!BuildConfig.DEBUG)
            Fabric.with(this, Crashlytics())
    }

    override fun applicationInjector(): AndroidInjector<out App> {
        return DaggerAppComponent.builder().create(this)
    }
}