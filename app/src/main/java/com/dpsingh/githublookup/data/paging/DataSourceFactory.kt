package com.dpsingh.githublookup.data.paging

import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DataSourceFactory<T> @Inject constructor(callingInterface: PagingInterface<T>) : DataSource.Factory<Long, T>() {

    val response: MediatorLiveData<PagingState<T>> = MediatorLiveData()
    private val dataSource: DataSourcePaging<T> by lazy {
        DataSourcePaging(response = response, repository = callingInterface)
    }

    fun retry(): Disposable? = this.dataSource.retry()

    fun clearTask(): Unit = this.dataSource.clearTask()

    fun invalidate(): Unit = this.dataSource.invalidate()

    override fun create(): DataSourcePaging<T> = this.dataSource

}

data class PagingState<T>(val state: Int, val data: PagedList<T>? = null, val error: Throwable? = null, val errorMessage: String? = null)
