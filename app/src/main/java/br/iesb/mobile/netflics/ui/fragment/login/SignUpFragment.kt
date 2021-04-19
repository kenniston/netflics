package br.iesb.mobile.netflics.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.FragmentSignupBinding
import br.iesb.mobile.netflics.domain.LoginResult
import br.iesb.mobile.netflics.viewmodel.LoginViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val viewmodel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewmodel = viewmodel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.result.observe(viewLifecycleOwner) {
            when (it) {
                is LoginResult.Success -> {
                    requireActivity().finish()
                    Toast.makeText(context, getText(R.string.signup_successfully), Toast.LENGTH_LONG).show()
                }
                is LoginResult.Error -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }

    }

}