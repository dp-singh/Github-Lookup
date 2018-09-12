package com.dpsingh.githublookup.data.local

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.dpsingh.githublookup.data.remote.response.Response
import javax.inject.Inject

open class RepoDataSource<T> @Inject constructor(callingInterface: PagingInterface<T>) : DataSource.Factory<Long, T>() {

    val response = MutableLiveData<Response<Int>>()
    private val dataSource: DataSourcePaging<T>

    init {
        dataSource = DataSourcePaging(response = response, repository = callingInterface)
    }

    fun retry(): Unit = this.dataSource.retry()

    fun clearTask(): Unit = this.dataSource.clearTask()

    override fun create(): DataSourcePaging<T> = this.dataSource

}