package br.iesb.mobile.netflics.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.FragmentLoginBinding
import br.iesb.mobile.netflics.domain.LoginResult
import br.iesb.mobile.netflics.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentLoginBinding
    private val viewmodel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
                is LoginResult.Success -> {
                    requireActivity().finish()
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                }
                is LoginResult.Error -> Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun login(v: View) {
        viewmodel.login()
    }

    @Suppress("UNUSED_PARAMETER")
    fun forgot(v: View) {
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    @Suppress("UNUSED_PARAMETER")
    fun signup(v: View) {
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    @Suppress("UNUSED_PARAMETER")
    fun loginWithGoogle(v: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        startForResult.launch(googleSignInClient.signInIntent)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        val account = task.result
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (task.isSuccessful) {
                    requireActivity().finish()
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                } else {
                    Toast.makeText(context, it.exception?.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

}