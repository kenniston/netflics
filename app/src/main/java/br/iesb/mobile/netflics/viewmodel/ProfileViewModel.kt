package br.iesb.mobile.netflics.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.domain.AppResult
import br.iesb.mobile.netflics.domain.Profile
import br.iesb.mobile.netflics.interactor.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: Application,
    private val interactor: ProfileInteractor
) : AndroidViewModel(app) {

    val currentProfile = MutableLiveData<Profile>()

    val profile1 = MutableLiveData(Profile("profile1", app.getString(R.string.new_profile)))
    val profile2 = MutableLiveData(Profile("profile2", app.getString(R.string.new_profile)))
    val profile3 = MutableLiveData(Profile("profile3", app.getString(R.string.new_profile)))
    val profile4 = MutableLiveData(Profile("profile4", app.getString(R.string.new_profile)))

    val result = MutableLiveData<AppResult<Nothing>>()

    fun createOrUpdateProfile(profileIndex: Int) {
        currentProfile.value = when (profileIndex) {
            1 -> profile1.value
            2 -> profile2.value
            3 -> profile3.value
            else -> profile4.value
        }
        viewModelScope.launch {
            result.value = interactor.createOrUpdateProfile(currentProfile.value!!)
        }
    }

}