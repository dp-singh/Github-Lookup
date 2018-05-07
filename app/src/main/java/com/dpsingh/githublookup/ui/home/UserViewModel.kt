package com.dpsingh.githublookup.ui.home

import android.arch.lifecycle.MutableLiveData
import com.dpsingh.githublookup.data.remote.response.Response
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.domain.repository.UserRepository
import com.dpsingh.githublookup.ui.base.BaseViewModel
import com.dpsingh.githublookup.utils.ViewState
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel<User>() {

    val listUser: MutableLiveData<List<User>> = MutableLiveData()
    fun loadUser(userName: String) {
        userRepository.getUser(userName)
                .doOnSubscribe { response.value = Response(ViewState.LOADING, null, null) }
                .subscribeBy(
                        onSuccess = {
                            response.value = Response(ViewState.SUCCESS, it, null)
                        },
                        onError = {
                            Timber.e(it)
                            response.value = Response(ViewState.ERROR, null, it)
                        }
                )
    }

    fun loadSearchHistory() {
        userRepository.loadSearchHistory().subscribeBy(
                onNext = listUser::postValue,
                onError = Timber::e
        )
    }

}
