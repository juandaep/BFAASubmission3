package com.example.submission3.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.adapter.FollowingAdapter
import com.example.submission3.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    private var receiveData: String? = null

    companion object {
        const val EXTRA_FOLLOWING = "extra_following"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_following, container, false)

        receiveData = arguments?.getString("sendData")

        setViewModel()
        return root
    }

    private fun setViewModel() {
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        if (arguments != null) {
            val username = arguments?.getString(EXTRA_FOLLOWING)
            followingViewModel.setFollowing(username.toString())
        }

        followingViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followingItem ->
            if (followingItem.size > 0) {
                adapter.setFollowing(followingItem)
            } else {
                tv_no_following.visibility = View.VISIBLE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showRecyclerListFollowing()
    }

    private fun showRecyclerListFollowing() {
        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = adapter
    }

}