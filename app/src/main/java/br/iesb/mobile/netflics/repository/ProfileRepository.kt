package br.iesb.mobile.netflics.repository

import br.iesb.mobile.netflics.domain.AppResult
import br.iesb.mobile.netflics.domain.Profile
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    suspend fun createOrUpdateProfile(p: Profile): AppResult<Nothing> = suspendCoroutine { nextStep ->
        val data = hashMapOf(
            "name" to p.name
        )
        firestore.collection("profile").document(p.id!!)
            .set(data)
            .addOnCompleteListener { op ->
                val res = if (op.isSuccessful) {
                    AppResult.Success()
                } else {
                    AppResult.Error(op.exception?.localizedMessage, op.exception)
                }
                nextStep.resume(res)
            }
    }
}