package com.example.submission3.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.ID
import com.example.submission3.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import java.sql.SQLException

class FavoriteHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private val INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavoriteHelper(context)
        }
    }

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var  db: SQLiteDatabase

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        db = databaseHelper.writableDatabase
    }

    fun queryAll(): Cursor {
        return db.query(
            DATABASE_TABLE, null, null, null, null, null, "$ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return db.query(
            DATABASE_TABLE, null, "$ID = ?", arrayOf(id), null, null, null, null,
        )
    }

    fun insert(values: ContentValues?): Long {
        return db.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        open()
        return db.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return db.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }



}