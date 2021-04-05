package br.iesb.mobile.netflics.interactor

import br.iesb.mobile.netflics.repository.LoginRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor (
    repo: LoginRepository
) {

    suspend fun login(email: String?, password: String?) {

    }

}