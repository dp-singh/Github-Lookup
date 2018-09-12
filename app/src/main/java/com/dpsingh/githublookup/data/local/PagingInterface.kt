package com.dpsingh.githublookup.data.local

import io.reactivex.Single

interface PagingInterface<T> {
    var data: String?
    fun getSearchData(): String = data ?: ""
    fun getPagingRepoCall(data: String, currentPage: Long, numberOfItems: Int): Single<List<T>>
    fun getPagingRepoCall(currentPage: Long, numberOfItems: Int): Single<List<T>> = getPagingRepoCall(getSearchData(), currentPage, numberOfItems)
}