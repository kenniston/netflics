package br.iesb.mobile.netflics.domain

data class LoginData(val message: String?)

sealed class LoginResult<out T: Any> {
    data class Success<out T: Any>(val data: T): LoginResult<T>()
    data class Error(val error: Throwable): LoginResult<Nothing>()
}