package br.iesb.mobile.netflics.ui.fragment.main.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.iesb.mobile.netflics.databinding.FragmentSelectProfileBinding
import br.iesb.mobile.netflics.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectProfileFragment : Fragment() {

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

        return binding.root
    }

    @Suppress("UNUSED_PARAMETER")
    fun createOrSelectProfile(v: View) {
        val tag = v.tag as String
        showProfileDialog(tag)
    }

    private fun showProfileDialog(profileId: String) {
        val alert = AlertDialog.Builder(context)
        val edittext = EditText(context)

        alert.setMessage("Informe o nome do novo Perfil:")
        alert.setTitle("Perfil")
        alert.setView(edittext)

        alert.setPositiveButton("Continuar") { dialog, _ ->
            val name = edittext.text.toString()
            viewmodel.profile.value?.id = profileId
            viewmodel.profile.value?.name = name
            viewmodel.createOrUpdateProfile()
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

}