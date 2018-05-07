package com.dpsingh.githublookup.extensions

fun <T : Any> T?.whenNotNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

fun <T : Any> T?.whenNull(f: (it: T?) -> Unit) {
    if (this == null) f(this)
}
