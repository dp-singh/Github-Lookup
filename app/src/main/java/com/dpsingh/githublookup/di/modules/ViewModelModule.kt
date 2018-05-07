package com.dpsingh.githublookup.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dpsingh.githublookup.di.keys.ViewModelKey
import com.dpsingh.githublookup.ui.home.UserViewModel
import com.dpsingh.githublookup.ui.repository_listing.RepositoryListViewModel
import com.dpsingh.githublookup.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindUsersViewModel(listUsersViewModel: UserViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RepositoryListViewModel::class)
    fun bindReposModel(listUsersViewModel: RepositoryListViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}