package br.iesb.mobile.netflics.interactor

import br.iesb.mobile.netflics.domain.LoginData
import br.iesb.mobile.netflics.domain.LoginResult
import br.iesb.mobile.netflics.repository.LoginRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor (
    private val repo: LoginRepository
) {

    suspend fun login(email: String?, password: String?): LoginResult<LoginData> {

        if (email.isNullOrBlank()) {
            TODO("Validate empty email")
        }

        if (password.isNullOrBlank()) {
            TODO("Validate empty password")
        }

        // TODO: "Validate email format"
        // TODO: "Validate password"

        return repo.login(email, password)
    }

}