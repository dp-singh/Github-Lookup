package com.dpsingh.githublookup.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dpsingh.githublookup.data.remote.response.Response


abstract class BaseViewModel<T> : ViewModel() {
    val response: MutableLiveData<Response<T>> = MutableLiveData()
}