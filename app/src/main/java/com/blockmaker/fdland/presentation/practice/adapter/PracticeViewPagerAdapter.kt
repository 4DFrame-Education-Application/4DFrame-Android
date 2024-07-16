package com.blockmaker.fdland.presentation.practice.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blockmaker.fdland.presentation.practice.view.PracticeAskewFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeChangeFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeCrossFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeLeanFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeOriginalFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeStandFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeStepFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeTogetherFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeTurnFragment
import com.blockmaker.fdland.presentation.practice.view.PracticeZigzagFragment

class PracticeViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PracticeOriginalFragment()
            1 -> PracticeStepFragment()
            2 -> PracticeTogetherFragment()
            3 -> PracticeChangeFragment()
            4 -> PracticeZigzagFragment()
            5 -> PracticeStandFragment()
            6 -> PracticeCrossFragment()
            7 -> PracticeTurnFragment()
            8 -> PracticeLeanFragment()
            9 -> PracticeAskewFragment()
            else -> PracticeOriginalFragment()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}