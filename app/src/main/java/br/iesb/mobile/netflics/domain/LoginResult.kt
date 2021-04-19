package br.iesb.mobile.netflics.domain

sealed class LoginResult<out T: Any> {
    data class Success<out T: Any>(val value: T? = null): LoginResult<T>()
    data class Error(val message: String?, val error: Throwable?): LoginResult<Nothing>()
}