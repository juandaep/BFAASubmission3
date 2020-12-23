package com.example.submission3.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission3.R
import com.example.submission3.view.fragment.FollowerFragment
import com.example.submission3.view.fragment.FollowingFragment

class SectionPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var username: String? = "username"

    @StringRes
    private val tabTitles = intArrayOf(R.string.followers, R.string.following)

    fun setData(newUsername: String?) {
        username = newUsername
    }

    private fun getData(): String? {
        return username
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowerFragment()
                val bundle = Bundle()
                bundle.putString(FollowerFragment.EXTRA_FOLLOWERS, getData())
                fragment.arguments = bundle
                Log.d("Data Follower", fragment.arguments.toString())
            }
            1 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString(FollowingFragment.EXTRA_FOLLOWING, getData())
                fragment.arguments = bundle
                Log.d("Data Following", fragment.arguments.toString())
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabTitles[position])
    }

}