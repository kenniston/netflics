package br.iesb.mobile.netflics.interactor

import br.iesb.mobile.netflics.domain.Profile
import br.iesb.mobile.netflics.repository.ProfileRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val repo: ProfileRepository
) {

    suspend fun createOrUpdateProfile(p: Profile) = repo.createOrUpdateProfile(p)

}