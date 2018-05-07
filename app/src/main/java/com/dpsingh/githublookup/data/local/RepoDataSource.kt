package com.dpsingh.githublookup.data.local

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import com.dpsingh.githublookup.data.remote.response.Response
import com.dpsingh.githublookup.domain.model.Repository
import com.dpsingh.githublookup.domain.repository.RepoListRepository
import com.dpsingh.githublookup.schedulers.BaseScheduler
import com.dpsingh.githublookup.utils.ViewState
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

open class RepoDataSource @Inject constructor(private val scheduler: BaseScheduler, private val repoListRepo: RepoListRepository) : DataSource.Factory<Long, Repository>() {

    var userName: String = ""
    var position: Int = 1

    var response = MutableLiveData<Response<Int>>()
    private var retryCompletable: Completable? = null

    open fun retry() {
        retryCompletable?.let {
            it.subscribe({ }, { throwable -> Timber.e(throwable.message) })
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    override fun create(): DataSource<Long, Repository> {
        return object : ItemKeyedDataSource<Long, Repository>() {

            override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Repository>) = fetchData(params.requestedLoadSize, callback)

            override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Repository>) = fetchData(params.requestedLoadSize, callback)

            override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Repository>) {}

            override fun getKey(item: Repository): Long = item.id.toLong()

            private fun fetchData(requestedLoadSize: Int, callback: LoadCallback<Repository>) {
                repoListRepo.getRepository(startPage = position, userName = userName, numberOfItems = requestedLoadSize)
                        .subscribeOn(scheduler.io())
                        .observeOn(scheduler.ui())
                        .doOnSubscribe({ response.postValue(Response(ViewState.LOADING)) })
                        .subscribeBy(
                                onSuccess = {
                                    response.postValue(Response(ViewState.SUCCESS))
                                    callback.onResult(it)
                                    position++
                                    setRetry(null)
                                },
                                onError = {
                                    response.postValue(Response(ViewState.ERROR, error = it))
                                    setRetry(Action { fetchData(requestedLoadSize, callback) })
                                }
                        )
            }

        }
    }

}