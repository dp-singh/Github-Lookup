package com.dpsingh.githublookup.di.modules

import com.dpsingh.githublookup.ui.home.HomeFragment
import com.dpsingh.githublookup.ui.repository_listing.RepositoryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuildersModule {

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment


    @ContributesAndroidInjector
    fun contributeDetailFragment(): RepositoryListFragment

}





