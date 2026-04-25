package com.practice.challengebemobile.core.data.exceptions

sealed class BaseException : Throwable() {

    data class Network(override val message: String? = null) : BaseException()

    data class HttpError(
        val code: Int,
        val errorBody: String?
    ) : BaseException()

    class Unknown : BaseException()
    class Timeout : BaseException()
}