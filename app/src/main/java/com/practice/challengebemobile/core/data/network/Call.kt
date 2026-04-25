package com.practice.challengebemobile.core.data.network

import com.practice.challengebemobile.core.data.exceptions.BaseException
import com.practice.challengebemobile.core.data.responses.BaseResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): BaseResponse<T> {
    return try {
        BaseResponse.Success(apiCall())
    } catch (e: IOException) {
        throw BaseException.Network(e.message)
    } catch (e: HttpException) {
        throw BaseException.HttpError(
            code = e.code(),
            errorBody = e.response()?.errorBody()?.string()
        )
    } catch (_: SocketTimeoutException) {
        throw BaseException.Timeout()
    } catch (_: Exception) {
        throw BaseException.Unknown()
    }
}