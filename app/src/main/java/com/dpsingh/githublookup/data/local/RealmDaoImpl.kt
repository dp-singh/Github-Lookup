package com.dpsingh.githublookup.data.local

import com.dpsingh.githublookup.domain.model.User
import io.reactivex.Flowable
import io.realm.Realm

class RealmDaoImpl(val realm: Realm) : RealmDao {

    override fun getUser(userName: String): User? {
        val user=realm.where(UserRealm::class.java)
                .equalTo("login", userName)
                .findFirst()

        return user?.toUser()
    }

    override fun storeOrUpdateUser(user: User) {
        realm.executeTransactionAsync({ realm -> realm.insertOrUpdate(user.toUserRealm()) })
    }

    override fun getAllUser(): Flowable<List<User>> {
        return realm.where(UserRealm::class.java)
                .findAllAsync()
                .asFlowable()
                .map {
                    it.map { userRealm ->
                        userRealm.toUser() //To change body of created functions use File | Settings | File Templates.
                    }
                }
    }
}