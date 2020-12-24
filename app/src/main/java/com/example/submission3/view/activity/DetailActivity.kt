package com.example.submission3.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.adapter.SectionPagerAdapter
import com.example.submission3.model.User
import com.example.submission3.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FAVORITE = "extra_favorite"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userDetail: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "User Detail"

        userDetail = intent.getParcelableExtra<User>(EXTRA_DETAIL) as User

        setViewModel()
        showLoading(true)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.setData(userDetail.username)
        view_pager.adapter = sectionPagerAdapter
        tab_layout.setupWithViewPager(view_pager)

    }

    private fun setViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(userDetail.username)
        detailViewModel.getDetailData().observe(this, Observer { userData ->
            if (userData != null) {
                Glide.with(this)
                    .load(userData.avatar)
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github)
                    .into(img_avatar_detail)
                tv_username_detail.text = userData.username
                tv_company_detail.text = userData.company_detail
                tv_location_detail.text = userData.location_detail
                tv_10.text = userData.repository_detail.toString()
                tv_15.text = userData.followers_detail.toString()
                tv_20.text = userData.following_detail.toString()
            }

            showLoading(false)
        })

    }

    private fun showLoading(sl: Boolean) {
        if (sl) {
            progressBar_detail.visibility = View.VISIBLE
        } else {
            progressBar_detail.visibility = View.GONE
        }
    }

}