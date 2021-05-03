package br.iesb.mobile.netflics.ui.fragment.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import br.iesb.mobile.netflics.databinding.FragmentProfileBottomSheetBinding
import br.iesb.mobile.netflics.domain.AppResult
import br.iesb.mobile.netflics.domain.Profile
import br.iesb.mobile.netflics.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileBottomSheetFragment(
    private val profile: Profile?,
    private val callback: (profile: Profile?) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProfileBottomSheetBinding
    private val viewmodel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewmodel = viewmodel

        viewmodel.currentProfile = MutableLiveData(profile)

        viewmodel.result.observe(viewLifecycleOwner) {
            when (it) {
                is AppResult.Success -> callback(viewmodel.currentProfile?.value)
                is AppResult.Error -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
            dismiss()
        }

        return binding.root
    }

    @Suppress("UNUSED_PARAMETER")
    fun save(v: View) {
        viewmodel.createOrUpdateProfile()
    }

    @Suppress("UNUSED_PARAMETER")
    fun cancel(v: View) {
        dismiss()
    }

}