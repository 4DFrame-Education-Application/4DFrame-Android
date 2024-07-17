//package com.blockmaker.fdland.presentation.build.adapter
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.blockmaker.fdland.presentation.build.view.BackFragment
//import com.blockmaker.fdland.presentation.build.view.FrontFragment
//import com.blockmaker.fdland.presentation.build.view.LeftFragment
//import com.blockmaker.fdland.presentation.build.view.RightFragment
//import com.blockmaker.fdland.presentation.build.view.UpFragment


//class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//
//    override fun createFragment(position: Int): Fragment {
//        return when (position) {
//            0 -> FrontFragment()
//            1 -> BackFragment()
//            2 -> LeftFragment()
//            3 -> RightFragment()
//            4 -> UpFragment()
//            else -> FrontFragment()
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return 5
//    }
//
//
//}
package com.blockmaker.fdland.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blockmaker.fdland.presentation.build.view.*

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val imageUrls: List<String>,
    private val jsonResults: String
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FrontFragment.newInstance(imageUrls.getOrNull(0) ?: "", jsonResults)
            1 -> BackFragment.newInstance(imageUrls.getOrNull(1) ?: "", jsonResults)
            2 -> LeftFragment.newInstance(imageUrls.getOrNull(2) ?: "", jsonResults)
            3 -> RightFragment.newInstance(imageUrls.getOrNull(3) ?: "", jsonResults)
            4 -> UpFragment.newInstance(imageUrls.getOrNull(4) ?: "", jsonResults)
            else -> FrontFragment.newInstance(imageUrls.getOrNull(0) ?: "", jsonResults)
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}