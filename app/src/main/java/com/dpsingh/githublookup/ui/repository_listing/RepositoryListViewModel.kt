package com.dpsingh.githublookup.ui.repository_listing

import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.dpsingh.githublookup.data.local.PagingInterface
import com.dpsingh.githublookup.data.local.PagingState
import com.dpsingh.githublookup.data.local.RepoDataSource
import com.dpsingh.githublookup.domain.model.Repository
import com.dpsingh.githublookup.domain.repository.RepoListRepository
import com.dpsingh.githublookup.utils.ViewState
import io.reactivex.Single
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(private var repoListRepository: RepoListRepository) : PagingViewModel<Repository>() {

    override var data: String? = null

    fun setUserName(userName: String) {
        this.data = userName
    }

    override fun getPagingRepoCall(data: String, currentPage: Long, numberOfItems: Int): Single<List<Repository>> {
        return repoListRepository.getRepository(data, currentPage, numberOfItems)
    }
}


abstract class PagingViewModel<T> : ViewModel(), PagingInterface<T> {

    private val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()


    private val repoDataSource: RepoDataSource<T> by lazy {
        return@lazy RepoDataSource(callingInterface = this).apply {
            //create a repository instance
            val livePageBuilder = LivePagedListBuilder<Long, T>(this, config).build()
            //map the builder data to get transformed in paging state
            response.addSource(livePageBuilder) { pageList: PagedList<T>? ->
                response.value = PagingState(ViewState.SUCCESS, pageList)
            }
        }
    }

    fun response() = repoDataSource.response

    fun retry() = repoDataSource.retry()

    fun reset() = repoDataSource.invalidate()

    override fun onCleared() {
        super.onCleared()
        repoDataSource.clearTask()
    }

    companion object {
        const val INITIAL_LOAD_SIZE = 10
        const val PAGE_SIZE = 10
        const val PREFETCH_DISTANCE = 10
    }
}

