package br.iesb.mobile.netflics.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.iesb.mobile.netflics.domain.AppResult
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
    val result = MutableLiveData<AppResult<Nothing>>()

    fun login() {
        viewModelScope.launch {
            result.value = interactor.login(email.value, password.value)
        }
    }

    fun signup() {
        viewModelScope.launch {
            result.value = interactor.signup(email.value, password.value)
        }
    }

    fun forgot() {
        viewModelScope.launch {
            result.value = interactor.forgot(email.value)
        }
    }

}