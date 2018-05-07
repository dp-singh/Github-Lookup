package com.dpsingh.githublookup.di.modules

import com.dpsingh.githublookup.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    fun contributeMainActivity(): HomeActivity

}