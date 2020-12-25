package com.example.consummerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consummerapp.R
import com.example.consummerapp.model.User
import kotlinx.android.synthetic.main.list_favorite.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var favoriteList = ArrayList<User>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.favoriteList.clear()
            }
            this.favoriteList.addAll(listFavorite)
            notifyDataSetChanged()
        }

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: User) {
            with(itemView) {
                tv_username.text = favorite.username
                Glide.with(context)
                    .load(favorite.avatar)
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github)
                    .into(img_avatar)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(favorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = this.favoriteList.size
}