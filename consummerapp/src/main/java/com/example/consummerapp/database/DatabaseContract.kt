package com.example.consummerapp.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.submission3"
    const val SCHEME = "content"

    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}