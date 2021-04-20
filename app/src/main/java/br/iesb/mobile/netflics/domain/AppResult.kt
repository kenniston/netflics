package br.iesb.mobile.netflics.domain

sealed class AppResult<out T: Any> {
    data class Success<out T: Any>(val value: T? = null): AppResult<T>()
    data class Error(val message: String?, val error: Throwable?): AppResult<Nothing>()
}