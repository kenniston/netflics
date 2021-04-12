package br.iesb.mobile.netflics.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.iesb.mobile.netflics.domain.LoginData
import br.iesb.mobile.netflics.domain.LoginResult
import br.iesb.mobile.netflics.interactor.LoginInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    app: Application,
    private val interactor: LoginInteractor
) : AndroidViewModel(app) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val result = MutableLiveData<LoginResult<LoginData>>()

    fun login() {
        viewModelScope.launch {
            try {
                result.value = interactor.login(email.value, password.value)
            } catch (t: Throwable) {
                Log.d("NETFLICS", "[LOGIN] Error on login: ${t.localizedMessage}")
            }
        }
    }


}