package com.example.submission3.service

import android.content.Context

class AlarmPreference(context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "alarm_preference"
        private const val DAILY_REMINDER = "daily_reminder"
    }

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setReminder(sr: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(DAILY_REMINDER, sr)
        editor.apply()
    }
}