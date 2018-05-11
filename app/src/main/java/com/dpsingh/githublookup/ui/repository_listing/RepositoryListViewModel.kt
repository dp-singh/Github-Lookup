package com.dpsingh.githublookup.ui.repository_listing

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.dpsingh.githublookup.data.local.RepoDataSource
import com.dpsingh.githublookup.domain.model.Repository
import com.dpsingh.githublookup.domain.repository.RepoListRepository
import com.dpsingh.githublookup.schedulers.BaseScheduler
import com.dpsingh.githublookup.ui.base.BaseViewModel
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(repoListRepository: RepoListRepository,
                                                  scheduler: BaseScheduler) : BaseViewModel<Int>() {


    var repoDataSource: RepoDataSource
    var repository: LiveData<PagedList<Repository>>

    fun setUserName(userName: String) {
        repoDataSource.userName = userName
    }

    fun getResponseData() = repoDataSource.response
    fun retry() = repoDataSource.retry()

    init {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .build()

        repoDataSource = RepoDataSource(scheduler, repoListRepository)
        repository = LivePagedListBuilder<Long, Repository>(repoDataSource, config).build()

    }

    companion object {
        const val INITIAL_LOAD_SIZE = 15
        const val PAGE_SIZE = 15
        const val PREFETCH_DISTANCE = 8
    }
}