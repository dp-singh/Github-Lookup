package com.dpsingh.githublookup.extensions

import retrofit2.HttpException

fun Throwable.customMessage(data: String): String {
    return when (this) {
        is HttpException ->
            when (this.code()) {
                in 300..399 -> "Temporary redirect "
                403 -> "API rate limit exceeded"
                404 -> "$data not found"
                in 400..499 -> "$data not valid"
                in 500..599 -> "Server error, try again "
                else -> "Something bad happened"
            }
        else -> "Something bad happened, Please try again"
    }
}