package com.example.submission3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.model.DetailUser
import com.example.submission3.model.User
import com.example.submission3.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userDetailUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

       // val dataFromMainActivity = intent.getStringExtra(EXTRA_DETAIL)
        userDetailUser = intent.getParcelableExtra<User>(EXTRA_DETAIL) as User

        detailViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(userDetailUser.username)
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
        })
    }

    private fun setMainViewModel(username: String?) {

    }

}