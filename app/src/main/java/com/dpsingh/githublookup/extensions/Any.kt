package com.dpsingh.githublookup.extensions

inline fun <T : Any> T?.whenNotNull(crossinline f: (it: T) -> Unit) {
    if (this != null) f(this)
}

inline fun <T : Any> T?.whenNull(crossinline f: (it: T?) -> Unit) {
    if (this == null) f(this)
}
