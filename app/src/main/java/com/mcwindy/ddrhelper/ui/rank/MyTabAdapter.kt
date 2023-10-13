package com.mcwindy.ddrhelper.ui.rank

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class MyTabAdapter(
    fa: FragmentActivity, private var totalTabs: Int = 3
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return ListTableFragment(position)
    }
}