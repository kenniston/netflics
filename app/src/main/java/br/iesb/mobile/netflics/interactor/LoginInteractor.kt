package br.iesb.mobile.netflics.interactor

import android.app.Application
import android.util.Patterns
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.domain.LoginData
import br.iesb.mobile.netflics.domain.LoginResult
import br.iesb.mobile.netflics.repository.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginInteractor @Inject constructor (
    private val repo: LoginRepository,
    private val app: Application
) {

    private fun validadeEmailAndPassword(email: String?, password: String?): Pair<String, String> {
        if (email.isNullOrBlank()) {
            throw Exception(app.getString(R.string.email_required))
        }

        if (password.isNullOrBlank()) {
            throw Exception(app.getString(R.string.password_required))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw Exception(app.getString(R.string.invalid_email))
        }

        if (password.length < 6) {
            throw Exception(app.getString(R.string.password_minimum_length))
        }

        return Pair(email, password)
    }

    suspend fun login(email: String?, password: String?): String {
        val (e, p) = validadeEmailAndPassword(email, password)
        return repo.login(e, p)
    }

    suspend fun signup(email: String?, password: String?): String {
        val (e, p) = validadeEmailAndPassword(email, password)
        return repo.signup(e, p)
    }

    suspend fun forgot(email: String?): String {
        if (email.isNullOrBlank()) {
            throw Exception(app.getString(R.string.email_required))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw Exception(app.getString(R.string.invalid_email))
        }

        return repo.forgot(email)
    }

}