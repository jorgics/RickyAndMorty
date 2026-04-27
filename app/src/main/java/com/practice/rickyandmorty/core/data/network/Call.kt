package com.practice.rickyandmorty.core.data.network

import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): BaseResponse<T> {
    return try {
        BaseResponse.Success(apiCall())
    } catch (e: IOException) {
        BaseResponse.Error(BaseException.Network(e.message))
    } catch (e: HttpException) {
        BaseResponse.Error(BaseException.HttpError(
            code = e.code(),
            errorBody = e.response()?.errorBody()?.string()
        ))
    } catch (_: SocketTimeoutException) {
        BaseResponse.Error(BaseException.Timeout())
    } catch (_: Exception) {
        BaseResponse.Error(BaseException.Unknown())
    }
}