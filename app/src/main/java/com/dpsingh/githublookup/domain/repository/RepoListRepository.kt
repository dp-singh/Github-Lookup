package com.dpsingh.githublookup.domain.repository

import com.dpsingh.githublookup.data.remote.Api
import com.dpsingh.githublookup.domain.model.Repository
import io.reactivex.Single
import javax.inject.Inject

open class RepoListRepository @Inject constructor(private val api: Api) {

    fun getRepository(userName: String, startPage: Int = 1, numberOfItems: Int = 10): Single<List<Repository>> {
        return api.getRepository(userName, startPage, numberOfItems)
    }
}