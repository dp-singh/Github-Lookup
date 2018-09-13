package com.dpsingh.githublookup.data.local

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import com.dpsingh.githublookup.data.remote.response.Response
import java.util.logging.Handler
import javax.inject.Inject

open class RepoDataSource<T> @Inject constructor(callingInterface: PagingInterface<T>) : DataSource.Factory<Long, T>() {

    val response = MutableLiveData<PagingState<T>>()
    private val dataSource: DataSourcePaging<T>

    init {
        dataSource = DataSourcePaging(response = response, repository = callingInterface)
    }

    fun retry(): Unit = this.dataSource.retry()

    fun clearTask(): Unit = this.dataSource.clearTask()

    fun invalidate():Unit=this.dataSource.invalidate()

    override fun create(): DataSourcePaging<T> = this.dataSource

}

data class PagingState<T>(val state:Int, val data: PagedList<T>?=null, val error: Throwable?=null, val errorMessage:String?=null)
