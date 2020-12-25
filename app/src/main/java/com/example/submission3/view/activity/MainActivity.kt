package com.example.submission3.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.adapter.UserAdapter
import com.example.submission3.model.User
import com.example.submission3.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Github User App"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        setViewModel()
        showUserList()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        search_bar.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search_bar.queryHint = resources.getString(R.string.hint_search)
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataFromGithubApi(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getDataFromGithubApi(user: String) {
        showLoading(true)
        mainViewModel.setUser(user)
        hideKeyboard()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.getUser().observe(this, Observer { userItem ->
            if (userItem.size > 0) {
                adapter.setData(userItem)
                showLoading(false)
            } else {
                Snackbar.make(main_activity, R.string.username_not_found, Snackbar.LENGTH_SHORT).show()
                showLoading(false)
            }
        })
    }

    private fun showUserList() {
        rv_search_user.layoutManager = LinearLayoutManager(this)
        rv_search_user.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })

    }

    private fun showSelectedUser(user: User) {
        val toDetailActivity = Intent(this@MainActivity, DetailActivity::class.java)
        toDetailActivity.putExtra(DetailActivity.EXTRA_DETAIL, user)
        toDetailActivity.putExtra(DetailActivity.EXTRA_FAVORITE, "favorite")
        startActivity(toDetailActivity)
    }

    private fun showLoading(sl: Boolean) {
        if (sl) {
            progress_search.visibility = View.VISIBLE
            tv_search.visibility = View.VISIBLE
        } else {
            progress_search.visibility = View.GONE
            tv_search.visibility = View.GONE
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideKey = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideKey.hideSoftInputFromWindow(view.windowToken, 0)
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_reminder -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.language_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
}