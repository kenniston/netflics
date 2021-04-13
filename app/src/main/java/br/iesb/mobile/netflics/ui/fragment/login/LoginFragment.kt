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
import br.iesb.mobile.netflics.databinding.FragmentLoginBinding
import br.iesb.mobile.netflics.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewmodel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewmodel = viewmodel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.result.observe(viewLifecycleOwner) {
            when (it) {
                "OK" -> {
                    requireActivity().finish()
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                }
                else -> Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun login() {
       viewmodel.login()
    }

    fun forgot() {
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    fun signup() {
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    fun loginWithGoogle() {

    }

}