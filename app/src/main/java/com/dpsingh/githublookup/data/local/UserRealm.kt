package com.dpsingh.githublookup.data.local

import com.dpsingh.githublookup.domain.model.User
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var login: String = ""
    var avatar_url: String? = null
    var name: String? = null
    var company: String? = null
    var location: String? = null
    var bio: String? = null
    var public_repos: Int = 0
    var public_gists: Int = 0
    var followers: Int = 0
    var following: Int = 0


    fun toUser(): User {
        return User(
                login = login,
                id = id,
                avatar_url = avatar_url,
                name = name,
                company = company,
                location = location,
                bio = bio,
                public_repos = public_repos,
                public_gists = public_gists,
                followers = followers,
                following = following
        )
    }
}
