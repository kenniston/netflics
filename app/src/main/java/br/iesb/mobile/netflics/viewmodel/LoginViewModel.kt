package br.iesb.mobile.netflics.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.iesb.mobile.netflics.domain.LoginData
import br.iesb.mobile.netflics.domain.LoginResult
import br.iesb.mobile.netflics.interactor.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    private val interactor: LoginInteractor
) : AndroidViewModel(app) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordCheck = MutableLiveData<String>()
    val result = MutableLiveData<String>()

    fun login() {
        viewModelScope.launch {
            try {
                result.value = interactor.login(email.value, password.value)
            } catch (t: Throwable) {
                Log.d("NETFLICS", "[LOGIN] Error on login: ${t.localizedMessage}")
                result.value = t.localizedMessage
            }
        }
    }

    fun forgot() {
        viewModelScope.launch {
            try {
                result.value = interactor.forgot(email.value)
            } catch (t: Throwable) {
                Log.d("NETFLICS", "[LOGIN] Error on forgot password: ${t.localizedMessage}")
                result.value = t.localizedMessage
            }
        }
    }

    fun signup() {
        viewModelScope.launch {
            try {
                result.value = interactor.signup(email.value, password.value)
            } catch (t: Throwable) {
                Log.d("NETFLICS", "[LOGIN] Error on forgot password: ${t.localizedMessage}")
                result.value = t.localizedMessage
            }
        }
    }


}