package com.practice.challengebemobile.core.data.responses

import com.practice.challengebemobile.core.data.exceptions.BaseException

sealed class BaseResponse<out T> {
    data class Success<T>(val data: T?) : BaseResponse<T>()
    data class Error(val exception: BaseException) : BaseResponse<Nothing>()
}