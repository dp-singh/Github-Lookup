package com.dpsingh.githublookup.data.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import com.dpsingh.githublookup.utils.ViewState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DataSourcePaging<T> constructor(val repository: PagingInterface<T>, val response: MutableLiveData<PagingState<T>>, private var position: Long = 1) : ItemKeyedDataSource<Long, T>() {

    private var retryCompletable: Completable? = null
    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<T>) = fetchData(params.requestedLoadSize, callback)

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<T>) = fetchData(params.requestedLoadSize, callback)

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<T>) {}

    override fun getKey(item: T): Long = System.currentTimeMillis()

    fun retry() = retryCompletable?.subscribe()


    fun clearTask() = disposable.dispose()

    private fun setRetry(action: Action?) {
        when (action) {
            null -> this.retryCompletable = null
            else -> this.retryCompletable = Completable.fromAction(action)
        }
    }

    private fun fetchData(requestedLoadSize: Int, callbackInitial: LoadCallback<T>) {
        repository.getPagingRepoCall(repository.getSearchData(), position, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.postValue(PagingState(state = ViewState.LOADING)) }
                .doOnDispose {
                    Timber.e("Disposed")
                }
                .subscribeBy(
                        onSuccess = {
                            if (it.isEmpty()) {
                                //if list is empty and position of paging is 1 it should show empty state else it should be completed state
                                if (position == 1L)
                                    response.postValue(PagingState(state = ViewState.EMPTY))
                                else
                                    response.postValue(PagingState(state = ViewState.COMPLETE))
                            }
                            callbackInitial.onResult(it)
                            setRetry(null)
                            position++

                        },
                        onError = {
                            response.postValue(PagingState(state = ViewState.ERROR, error = it))
                            setRetry(Action { fetchData(requestedLoadSize, callbackInitial) })
                        }
                ).addTo(disposable)
    }
}
