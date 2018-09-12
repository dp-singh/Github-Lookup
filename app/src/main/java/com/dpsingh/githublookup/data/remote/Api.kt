package com.dpsingh.githublookup.data.remote

import com.dpsingh.githublookup.domain.model.Repository
import com.dpsingh.githublookup.domain.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface Api {

    @GET("users/{user_name}/repos")
    fun getRepository(@Path("user_name") userName: String, @Query("page") page: Long = 1, @Query("per_page") per_page: Int = 10): Single<List<Repository>>


    @GET("users/{user_name}")
    fun getUserDetails(@Path("user_name") userName: String): Single<User>

}