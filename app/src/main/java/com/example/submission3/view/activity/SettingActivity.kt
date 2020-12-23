package com.example.submission3.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.submission3.R
import com.example.submission3.service.AlarmPreference
import com.example.submission3.service.AlarmService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReminder: AlarmService
    private lateinit var alarmPreference: AlarmPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        alarmReminder = AlarmService()
        alarmPreference = AlarmPreference(this)

        reminder_switch.isChecked = alarmReminder.setAlarm(this)
        reminder_switch.setOnCheckedChangeListener {_, isChecked ->
            if (isChecked) {
                alarmReminder.setRepeatAlarm(this)
                alarmPreference.setReminder(true)
                Snackbar.make(
                    activity_setting,
                    getString(R.string.switch_on),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                alarmReminder.cancelRepeatAlarm(this)
                alarmPreference.setReminder(false)
                Snackbar.make(
                    activity_setting,
                    getString(R.string.switch_off),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }
}