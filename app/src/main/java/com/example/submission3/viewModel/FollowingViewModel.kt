package com.example.submission3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.AppConstant
import com.example.submission3.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username: String) {
        val followingItem = ArrayList<User>()

        val url = "https://api.github.com/users/$username/following"
        val apiKey = AppConstant.API_KEY

        val client = AsyncHttpClient()
        client.addHeader("Authorization", " token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val responseObject = responseArray.getJSONObject(i)
                        val listArray = User()
                        listArray.avatar = responseObject.getString("avatar_url")
                        listArray.username = responseObject.getString("login")
                        followingItem.add(listArray)
                    }
                    listFollowing.postValue(followingItem)
                } catch (e: Exception) {
                    Log.d("FollowingActivity", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}