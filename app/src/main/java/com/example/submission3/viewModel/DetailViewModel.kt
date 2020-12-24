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

class DetailViewModel : ViewModel() {

    val detailData = MutableLiveData<User>()

    fun setDetailUser(username: String?) {
        val url = "https://api.github.com/users/$username"
        val apiKey = AppConstant.API_KEY

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token: $apiKey")
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
                    val detailUser = User()
                    detailUser.username = responseObject.getString("login")
                    detailUser.avatar = responseObject.getString("avatar_url")
                    detailUser.company_detail = responseObject.getString("company")
                    if (detailUser.company_detail == "null") detailUser.company_detail =
                        " -" else detailUser.company_detail =
                        detailUser.company_detail
                    detailUser.location_detail = responseObject.getString("location")
                    if (detailUser.location_detail == "null") detailUser.location_detail =
                        " -" else detailUser.location_detail =
                        detailUser.company_detail
                    detailUser.repository_detail = responseObject.getInt("public_repos")
                    detailUser.followers_detail = responseObject.getInt("followers")
                    detailUser.following_detail = responseObject.getInt("following")
                    detailData.postValue(detailUser)
                } catch (e: Exception) {
                    Log.d("DetailActivity", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("MainActivity", error?.message.toString())
            }
        })
    }

    fun getDetailData(): LiveData<User> {
        return detailData
    }
}