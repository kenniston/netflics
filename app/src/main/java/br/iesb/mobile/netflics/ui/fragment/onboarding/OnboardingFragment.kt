package br.iesb.mobile.netflics.ui.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.FragmentOnboardingBinding
import br.iesb.mobile.netflics.ui.adapter.OnboardingAdapter
import br.iesb.mobile.netflics.ui.fragment.onboarding.screen.OnboardingFirstScreenFragment
import br.iesb.mobile.netflics.ui.fragment.onboarding.screen.OnboardingSecondScreenFragment
import br.iesb.mobile.netflics.ui.fragment.onboarding.screen.OnboardingThirdScreenFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        binding.fragment = this
        binding.lifecycleOwner = this

        val pages = arrayListOf(
            OnboardingFirstScreenFragment(),
            OnboardingSecondScreenFragment(),
            OnboardingThirdScreenFragment()
        )

        binding.vpOnboarding.adapter = OnboardingAdapter(
            pages,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.wormDotsIndicator.setViewPager2(binding.vpOnboarding)

        return binding.root
    }

    @Suppress("UNUSED_PARAMETER")
    fun start(v: View) {
        findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
    }

}