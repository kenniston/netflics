package br.iesb.mobile.netflics.ui.fragment.main.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.iesb.mobile.netflics.databinding.FragmentDownloadBinding

class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

}