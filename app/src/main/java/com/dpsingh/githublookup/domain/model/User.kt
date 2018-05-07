package com.dpsingh.githublookup.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.dpsingh.githublookup.data.local.UserRealm
import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id")
        val id: Long,
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatar_url: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("company")
        val company: String?,
        @SerializedName("location")
        val location: String?,
        @SerializedName("bio")
        val bio: String?,
        @SerializedName("public_repos")
        val public_repos: Int,
        @SerializedName("public_gists")
        val public_gists: Int,
        @SerializedName("followers")
        val followers: Int,
        @SerializedName("following")
        val following: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(login)
        writeString(avatar_url)
        writeString(name)
        writeString(company)
        writeString(location)
        writeString(bio)
        writeInt(public_repos)
        writeInt(public_gists)
        writeInt(followers)
        writeInt(following)
    }

    fun toUserRealm(): UserRealm {
        val userRealm = UserRealm()
        userRealm.login = login
        userRealm.id = id
        userRealm.avatar_url = avatar_url
        userRealm.name = name
        userRealm.company = company
        userRealm.location = location
        userRealm.bio = bio
        userRealm.public_repos = public_repos
        userRealm.public_gists = public_gists
        userRealm.followers = followers
        userRealm.following = following
        return userRealm
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}