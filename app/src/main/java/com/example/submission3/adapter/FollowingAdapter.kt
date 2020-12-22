package com.example.submission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.model.User
import kotlinx.android.synthetic.main.list_user.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val followingList = ArrayList<User>()

    fun setFollowing(items: ArrayList<User>) {
        followingList.clear()
        followingList.addAll(items)
        notifyDataSetChanged()
    }

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(following: User) {
            with(itemView) {
                Glide.with(this)
                    .load(following.avatar)
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github)
                    .into(img_avatar)
                tv_username.text = following.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followingList[position])
    }

    override fun getItemCount(): Int = followingList.size
}