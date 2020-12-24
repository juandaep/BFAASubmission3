package com.example.submission3.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.adapter.FavoriteAdapter
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.submission3.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = "Your Favorite"

        loadFavoriteAsync()
        showFavoriteRecyclerView()
        showLoading(true)
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main){
            val deferredFavorite = async(Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorite = deferredFavorite.await()
            if (favorite.size > 0) {
                showLoading(true)
                adapter.favoriteList = favorite
            } else {
                showLoading(false)
                adapter.favoriteList = ArrayList()
                Snackbar.make(activity_favorite, R.string.no_favorite, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFavoriteRecyclerView() {
        adapter = FavoriteAdapter()
        rv_favorite.adapter = adapter
        rv_favorite.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(sl : Boolean) {
        if (sl) {
            progress_favorite.visibility = View.VISIBLE
        } else {
            progress_favorite.visibility = View.GONE
        }
    }
}