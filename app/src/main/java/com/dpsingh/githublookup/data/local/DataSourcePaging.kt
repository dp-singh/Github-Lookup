package com.dpsingh.githublookup.data.local

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import com.dpsingh.githublookup.data.remote.response.Response
import com.dpsingh.githublookup.utils.ViewState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DataSourcePaging<T> constructor(val repository: PagingInterface<T>, val response: MutableLiveData<PagingState<T>>, var position: Long = 1) : PageKeyedDataSource<Long, T>() {


    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, T>) = fetchData(params.requestedLoadSize, callbackInitial = callback)

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, T>) = fetchData(params.requestedLoadSize, callback = callback)

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {}

    private var retryCompletable: Completable? = null
    private var disposable: CompositeDisposable = CompositeDisposable()

    fun retry() {
        retryCompletable?.subscribe()?.addTo(disposable)
    }

    fun clearTask() = disposable.dispose()

    private fun setRetry(action: Action?) {
        when (action) {
            null -> this.retryCompletable = null
            else -> this.retryCompletable = Completable.fromAction(action)
        }
    }


    private fun fetchData(requestedLoadSize: Int, callbackInitial: LoadInitialCallback<Long, T>? = null, callback: LoadCallback<Long, T>? = null) {
        repository.getPagingRepoCall(repository.getSearchData(), position, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.postValue(PagingState(state = ViewState.LOADING)) }
                .subscribeBy(
                        onSuccess = {
                            position++
                            callback?.onResult(it, position)
                            callbackInitial?.onResult(it, null, position)
                            setRetry(null)
                        },
                        onError = {
                            response.postValue(PagingState(state = ViewState.ERROR, error = it))
                            setRetry(Action { fetchData(requestedLoadSize, callbackInitial, callback) })
                        }
                ).addTo(disposable)
    }
}
