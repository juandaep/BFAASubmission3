package com.example.consummerapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consummerapp.R
import com.example.consummerapp.adapter.FavoriteAdapter
import com.example.consummerapp.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.consummerapp.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter:  FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFavoriteAsync()
        showFavoriteList()

    }

    private fun showFavoriteList() {
        adapter = FavoriteAdapter()
        rv_favorite.adapter = adapter
        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(this)
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main){
            progress_favorite.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progress_favorite.visibility = View.INVISIBLE
            val favoriteUser = deferredFavorite.await()
            if (favoriteUser.size > 0) {
                adapter.favoriteList = favoriteUser
            } else {
                adapter.favoriteList = ArrayList()
                Snackbar.make(main_activity, R.string.no_favorite, Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}