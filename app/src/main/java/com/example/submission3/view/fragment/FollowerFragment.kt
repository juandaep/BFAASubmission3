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
import com.example.submission3.adapter.FollowerAdapter
import com.example.submission3.viewModel.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {

    private lateinit var adapter: FollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    private var receiveData: String? = null

    companion object {
        const val EXTRA_FOLLOWERS = "extra_followers"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_follower, container, false)

        receiveData = arguments?.getString("sendData")

        setViewModel()
        return root
    }

    private fun setViewModel() {
        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()

        followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)
        if (arguments != null) {
            val username = arguments?.getString(EXTRA_FOLLOWERS)
            followerViewModel.setFollower(username.toString())
        }

        followerViewModel.getFollower().observe(viewLifecycleOwner, Observer { followersItem ->
            if (followersItem.size > 0) {
                adapter.setFollower(followersItem)
            } else {
                tv_no_followers.visibility = View.VISIBLE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerListFollower()
    }

    private fun showRecyclerListFollower() {
        rv_follower.layoutManager = LinearLayoutManager(activity)
        rv_follower.adapter = adapter
    }

}