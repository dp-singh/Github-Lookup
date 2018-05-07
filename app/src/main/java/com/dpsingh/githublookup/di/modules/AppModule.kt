package com.dpsingh.githublookup.di.modules

import com.dpsingh.githublookup.data.local.RealmDao
import com.dpsingh.githublookup.data.local.RealmDaoImpl
import com.dpsingh.githublookup.data.remote.Api
import com.dpsingh.githublookup.schedulers.BaseScheduler
import com.dpsingh.githublookup.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduler(): BaseScheduler {
        return SchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideRealm(): RealmDao {
        val config = RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(1)
                .build()

        return RealmDaoImpl(Realm.getInstance(config))
    }

}