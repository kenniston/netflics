package br.iesb.mobile.netflics.ui.fragment.main.profile

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import br.iesb.mobile.netflics.databinding.FragmentSelectProfileBinding
import br.iesb.mobile.netflics.domain.AppResult
import br.iesb.mobile.netflics.ui.activity.NetFlicsActivity
import br.iesb.mobile.netflics.ui.component.AnimatedProfile
import br.iesb.mobile.netflics.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectProfileFragment : Fragment(), LifecycleObserver {

    private lateinit var binding: FragmentSelectProfileBinding
    private val viewmodel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSelectProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewmodel = viewmodel

        binding.ivProfile1.setOnEditClickListener { edit(it) }
        binding.ivProfile2.setOnEditClickListener { edit(it) }
        binding.ivProfile3.setOnEditClickListener { edit(it) }
        binding.ivProfile4.setOnEditClickListener { edit(it) }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        activity?.lifecycle?.removeObserver(this)
        loadProfiles()
    }

    private fun loadProfiles() {
        val activity = requireActivity() as? NetFlicsActivity
        activity?.showLoading()
        viewmodel.result.observe(viewLifecycleOwner) {
            activity?.hideLoading()
            if (it is AppResult.Error) {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
            this.binding.profileMotionLayout.transitionToEnd()
        }
        viewmodel.loadProfiles()
    }

    private fun showProfileDialog(index: Int, control: AnimatedProfile) {
        val alert = AlertDialog.Builder(context)
        val edittext = EditText(context)

        alert.setMessage("Informe o nome do novo Perfil:")
        alert.setTitle("Perfil")
        alert.setView(edittext)

        alert.setPositiveButton("Continuar") { dialog, _ ->
            val name = edittext.text.toString()
            viewmodel.currentProfile?.value?.id = "Profile$index"
            viewmodel.currentProfile?.value?.name = name
            viewmodel.createOrUpdateProfile()
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    @Suppress("UNUSED_PARAMETER")
    fun createOrSelectProfile(v: View) {
        val tag = (v.tag as String).toInt()
        viewmodel.selectProfile(tag)
        viewmodel.currentProfile?.value?.let {
            if (it.id == null) {
                showProfileDialog(tag, v as AnimatedProfile)
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun animation(v: View) {
        (v as AnimatedProfile).profileAnimatedCounter = !v.profileAnimatedCounter
    }

    private fun edit(v: AnimatedProfile) {
        val index = (v.tag as String).toInt()
        viewmodel.selectProfile(index)
        ProfileBottomSheetFragment(viewmodel.currentProfile?.value?.copy()) { profile ->
            when (index) {
                1 -> viewmodel.profile1.value = profile
                2 -> viewmodel.profile2.value = profile
                3 -> viewmodel.profile3.value = profile
                4 -> viewmodel.profile4.value = profile
            }
        }.show(requireActivity().supportFragmentManager, "editProfile")
    }

}