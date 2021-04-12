package br.iesb.mobile.netflics.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this

        return binding.root
    }

    @SuppressWarnings
    fun login(v: View) {

    }

    @SuppressWarnings
    fun forgot(v: View) {
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    @SuppressWarnings
    fun signup(v: View) {
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    @SuppressWarnings
    fun loginWithGoogle(v: View) {

    }

}