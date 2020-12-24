package com.example.submission3.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.ID
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_NAME = "db_favorite"
            private const val DATABASE_VERSION = 1

            private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                    "(${ID} INTEGER PRIMARY KEY," +
                    "$USERNAME TEXT NOT NULL UNIQUE," +
                    "$AVATAR TEXT_NOT_NULL)"
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
        Log.d("SQLite Database", "$DATABASE_NAME create")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}