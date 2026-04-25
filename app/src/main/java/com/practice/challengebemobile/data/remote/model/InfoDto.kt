package com.practice.challengebemobile.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoDto (
    @field:Json(name = "count")
    val count: Int?,
    @field:Json(name = "pages")
    val pages: Int?,
    @field:Json(name = "next")
    val next: String?,
    @field:Json(name = "prev")
    val prev: String?
)