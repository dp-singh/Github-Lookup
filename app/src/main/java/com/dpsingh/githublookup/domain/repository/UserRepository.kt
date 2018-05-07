package com.dpsingh.githublookup.domain.repository

import com.dpsingh.githublookup.data.local.RealmDao
import com.dpsingh.githublookup.data.remote.Api
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.schedulers.BaseScheduler
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

 open class UserRepository @Inject constructor(private val api: Api, private val realmDao: RealmDao, private val scheduler: BaseScheduler) {

    open fun getUser(userName: String): Single<User> {
        val user = realmDao.getUser(userName)

        return when (user) {
            null -> api.getUserDetails(userName)
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui())
                    .doAfterSuccess {
                        realmDao.storeOrUpdateUser(it)
                    }

            else -> Single.just(user)
        }
    }

    open fun loadSearchHistory(): Flowable<List<User>> = realmDao.getAllUser()

}
