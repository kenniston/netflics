package br.iesb.mobile.netflics.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(
    private val list: ArrayList<Fragment>,
    fagmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fagmentManager, lifecycle) {

    override fun getItemCount() = list.size
    override fun createFragment(position: Int) = list[position]

}