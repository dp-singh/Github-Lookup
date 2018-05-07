package com.dpsingh.githublookup.data.local

import com.dpsingh.githublookup.domain.model.User
import io.reactivex.Flowable

interface RealmDao {

    fun getUser(userName: String): User?

    fun getAllUser(): Flowable<List<User>>

    fun storeOrUpdateUser(user: User)

}