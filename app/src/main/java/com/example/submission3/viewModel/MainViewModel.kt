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
import org.json.JSONObject

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(username: String) {
        val listItem = ArrayList<User>()

        val url = "https://api.github.com/search/users?q=$username"
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
                    val responseObject = JSONObject(result)
                    val listArray = responseObject.getJSONArray("items")

                    for (i in 0 until listArray.length()) {
                        val itemUser = listArray.getJSONObject(i)
                        val user = User()
                        user.username = itemUser.getString("login")
                        user.avatar = itemUser.getString("avatar_url")
                        listItem.add(user)
                    }

                    listUser.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("MainActivity", e.message.toString())
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

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}
