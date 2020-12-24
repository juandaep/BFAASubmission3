package com.example.submission3.view.activity

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.adapter.SectionPagerAdapter
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.example.submission3.helper.MappingHelper
import com.example.submission3.model.User
import com.example.submission3.viewModel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FAVORITE = "extra_favorite"
    }

    private lateinit var detailViewModel: DetailViewModel
 //   private lateinit var userDetail: User
    private lateinit var uriWithId: Uri
    private var favorite = false
    private var userItem: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "User Detail"

        userItem = intent.getParcelableExtra<User>(EXTRA_DETAIL) as User

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + userItem?.id)

        setViewModel()
        showLoading(true)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.setData(userItem?.username)
        view_pager.adapter = sectionPagerAdapter
        tab_layout.setupWithViewPager(view_pager)

        setFavoriteData()
        fab_favorite.setOnClickListener(this)
    }

    private fun setViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(userItem?.username)
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

    private fun setFavoriteData() {
        val dataFavorite = contentResolver?.query(uriWithId, null, null, null, null)
        val dataObject  = MappingHelper.mapCursorToArrayList(dataFavorite)
        for (data in dataObject) {
            if (this.userItem?.username == data.username) {
                fab_favorite.setImageResource(R.drawable.ic_favorite_fill)
                favorite = true
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_favorite ->
                sendFavorite()
        }
    }

    private fun sendFavorite() {
        if (favorite) {
            userItem.let {
                contentResolver.delete(uriWithId, null, null)
                fab_favorite.setImageResource(R.drawable.ic_favorite_border)
                Snackbar.make(detail_activity, userItem?.username + " " + this.getString(R.string.remove_from_favorite), Snackbar.LENGTH_SHORT).show()
                favorite = false
            }
        } else {
            val values = ContentValues()
            values.put(USERNAME, userItem?.username)
            values.put(AVATAR, userItem?.avatar)
            contentResolver.insert(CONTENT_URI, values)
            userItem?.username
            fab_favorite.setImageResource(R.drawable.ic_favorite_fill)
            Snackbar.make(detail_activity, userItem?.username + " " + this.getString(R.string.add_to_favorite), Snackbar.LENGTH_SHORT).show()
            favorite = true
        }
    }

    private fun showLoading(sl: Boolean) {
        if (sl) {
            progressBar_detail.visibility = View.VISIBLE
        } else {
            progressBar_detail.visibility = View.GONE
        }
    }

}