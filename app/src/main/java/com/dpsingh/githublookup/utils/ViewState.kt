package com.dpsingh.githublookup.utils


class ViewState {
    companion object {
        const val LOADING = 1
        const val ERROR = 2
        const val SUCCESS = 3
        const val COMPLETE = 4
    }
}

class ViewType {
    companion object {
        const val HEADER = 11
        const val FOOTER = 12
        const val CONTENT = 13
        const val EMPTY_CONTENT = 14
    }
}