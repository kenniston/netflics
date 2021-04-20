package br.iesb.mobile.netflics.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.iesb.mobile.netflics.domain.AppResult
import br.iesb.mobile.netflics.domain.Profile
import br.iesb.mobile.netflics.interactor.LoginInteractor
import br.iesb.mobile.netflics.interactor.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: Application,
    private val interactor: ProfileInteractor
) : AndroidViewModel(app) {

    val profile = MutableLiveData<Profile>()
    val result = MutableLiveData<AppResult<Nothing>>()

    fun createOrUpdateProfile() {
        viewModelScope.launch {
            result.value = interactor.createOrUpdateProfile(profile.value!!)
        }
    }

}