package com.example.submission3.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission3.R

class SectionPagerAdapter(
    private val context: Context,
    dataFromDetailActivity: String?,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.followers, R.string.following)

    private val sendData = dataFromDetailActivity

//    dataFromDetailActivityoverride fun getItemId(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> {
//                fragment = FollowersFragment()
//                val bundle = Bundle()
//
//            }
//        }
//    }
    override fun getCount(): Int = 2
    override fun getItem(position: Int): Fragment {
        TODO("Not yet implemented")
    }


}