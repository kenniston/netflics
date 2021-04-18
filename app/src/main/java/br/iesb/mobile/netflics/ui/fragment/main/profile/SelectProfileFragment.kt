package br.iesb.mobile.netflics.ui.fragment.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.iesb.mobile.netflics.databinding.FragmentSelectProfileBinding
import br.iesb.mobile.netflics.viewmodel.ProfileViewModel

class SelectProfileFragment : Fragment() {

    private lateinit var binding: FragmentSelectProfileBinding
    private val viewmodel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewmodel = viewmodel

        return binding.root
    }

}