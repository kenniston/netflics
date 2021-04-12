package br.iesb.mobile.netflics.repository

import android.content.Context
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.domain.LoginData
import br.iesb.mobile.netflics.domain.LoginResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
    @ApplicationContext val context: Context
) {

    suspend fun login(email: String, pass:String): LoginResult<LoginData> = suspendCoroutine { nextStep ->
        val operation = auth.signInWithEmailAndPassword(email, pass)
        operation.addOnCompleteListener { op ->
            val res = if (op.isSuccessful) {
                val data = LoginData(context.getString(R.string.login_success))
                LoginResult.Success(data)
            } else {
                LoginResult.Error(op.exception as Throwable)
            }
            nextStep.resume(res)
        }
    }

}