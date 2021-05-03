package br.iesb.mobile.netflics.ui.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.FragmentSplashscreenBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashscreenBinding

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashscreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(1600)

            if (auth.currentUser != null) {
                requireActivity().finish()
                findNavController().navigate(R.id.action_splashScreenFragment_to_mainActivity)
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_onboardingFragment)
            }
        }

        return binding.root
    }

}