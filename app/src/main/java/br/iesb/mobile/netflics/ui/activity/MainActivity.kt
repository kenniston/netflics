package br.iesb.mobile.netflics.ui.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import br.iesb.mobile.netflics.R
import br.iesb.mobile.netflics.databinding.ActivityMainBinding
import br.iesb.mobile.netflics.service.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class MainActivity : AppCompatActivity(), NetFlicsActivity, NetFlicsMainActivity {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        networkMonitor.observe(this) { connected ->
            binding.networkLayout.visibility = if (!connected) View.VISIBLE else View.GONE
        }

        val navigationController = Navigation.findNavController(this, R.id.mainNavigationFragment)
        binding.bvMainBar.setupWithNavController(navigationController)
    }

    override fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loadingLayout.visibility = View.GONE
    }

    override fun showBottomNavigation() {
        binding.bvMainBar.animate().setInterpolator(AccelerateDecelerateInterpolator())
            .translationY(0f)
    }

    override fun hideBottomNavigation() {
        binding.bvMainBar.animate().setInterpolator(AccelerateDecelerateInterpolator())
            .translationY(150f)
    }

}