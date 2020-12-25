package com.example.consummerapp.helper

import android.database.Cursor
import com.example.consummerapp.database.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.consummerapp.database.DatabaseContract.FavoriteColumns.Companion.ID
import com.example.consummerapp.database.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.example.consummerapp.model.User

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?) : ArrayList<User>{
        val favoriteList = ArrayList<User>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val userItem = User()
                userItem.id = getInt(getColumnIndexOrThrow(ID))
                userItem.username = getString(getColumnIndexOrThrow(USERNAME))
                userItem.avatar = getString(getColumnIndexOrThrow(AVATAR))
                favoriteList.add(userItem)
            }
        }
        return favoriteList
    }
}