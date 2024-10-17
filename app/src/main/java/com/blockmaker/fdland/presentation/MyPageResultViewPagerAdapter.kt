package com.blockmaker.fdland.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class MyPageResultViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FrontFragmentList()
            1 -> BackFragmentList()
            2 -> RightFragmentList()
            3 -> LeftFragmentList()
            4 -> UpFragmentList()
            else -> FrontFragmentList()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}