package com.practice.rickyandmorty.core.extensions

import androidx.core.net.toUri

fun String?.extractPageNumber(): Int? {
    if (this.isNullOrBlank()) return null
    return (this.toUri().getQueryParameter("page"))?.toIntOrNull()
}