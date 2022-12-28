package com.task.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyAlarmReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationGenerator by inject<NotificationGenerator>()

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("MyAlarmReceiver*", "Alarm just fired")

        CoroutineScope(Dispatchers.Default).launch {
            notificationGenerator.onMessageReceived()
        }
    }
}