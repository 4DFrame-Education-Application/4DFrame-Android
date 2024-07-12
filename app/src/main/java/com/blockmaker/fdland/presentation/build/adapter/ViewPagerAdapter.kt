package com.blockmaker.fdland.presentation.build.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blockmaker.fdland.presentation.build.view.BackFragment
import com.blockmaker.fdland.presentation.build.view.FrontFragment
import com.blockmaker.fdland.presentation.build.view.LeftFragment
import com.blockmaker.fdland.presentation.build.view.RightFragment
import com.blockmaker.fdland.presentation.build.view.UpFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FrontFragment()
            1 -> BackFragment()
            2 -> LeftFragment()
            3 -> RightFragment()
            4 -> UpFragment()
            else -> FrontFragment()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }


}
