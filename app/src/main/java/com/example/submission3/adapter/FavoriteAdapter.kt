package com.example.submission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.model.User
import com.example.submission3.view.activity.DetailActivity
import kotlinx.android.synthetic.main.list_user.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    var favoriteList = ArrayList<User>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.favoriteList.clear()
            }
            this.favoriteList.addAll(listFavorite)
            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                tv_username.text = user.username
                Glide.with(context)
                    .load(user.avatar)
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github)
                    .into(img_avatar)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user)
                val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL, user)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, "UserFavorite")
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = this.favoriteList.size
}