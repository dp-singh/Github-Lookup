package com.dpsingh.githublookup.data.remote.response


data class Response<out T>(
        val status: Int,
        val data: T? = null,
        val error: Throwable? = null
)