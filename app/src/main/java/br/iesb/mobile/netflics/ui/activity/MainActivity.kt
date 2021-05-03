package br.iesb.mobile.netflics.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.ActivityMainBinding
import br.iesb.mobile.netflics.service.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class MainActivity : AppCompatActivity(), NetFlicsActivity {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        networkMonitor.observe(this) { connected ->
            binding.networkLayout.visibility =  if (!connected) View.VISIBLE else View.GONE
        }
    }

    override fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loadingLayout.visibility = View.GONE
    }

}