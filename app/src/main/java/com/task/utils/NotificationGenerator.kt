package com.task.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.task.R
import com.task.model.DrinkState
import com.task.ui.MainActivity
import com.task.viewmodel.FavDrinksViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random


class NotificationGenerator(private val context: Context) : KoinComponent {
    private val favDrinksViewModel by inject<FavDrinksViewModel>()

    suspend fun onMessageReceived() {
        favDrinksViewModel.getAllFavDrinks()
        favDrinksViewModel.favDrinksData.collect {
            when (it) {
                is DrinkState.SuccessDrinks -> {
                    val list = it.drinkList
                    if (list.isNotEmpty()) {
                        val randomIndex = Random.nextInt(list.size)
                        val randomModel = list[randomIndex]
                        Handler(context.mainLooper).post {
                            randomModel.apply {
                                sendNotification(strDrinkThumb, strDrink, strAlcoholic)
                            }
                        }
                    } else {
                        context.showToast("Need some drinks open app now.")
                    }
                }
                else -> {}
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(
        icon: String,
        title: String,
        alcoholic: String
    ) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //Remote Views
        val remoteViews = RemoteViews(context.packageName, R.layout.notification_view)
        remoteViews.setTextViewText(R.id.tv_title, title)
        remoteViews.setTextViewText(R.id.tv_short_desc, alcoholic)

        //Notification Parameters
        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViews)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        //Build Notification
        val notificationID = getNextInt()
        notificationManager.notify(notificationID, notificationBuilder.build())


        val notificationTarget = NotificationTarget(
            context,
            R.id.iv_icon,
            remoteViews,
            notificationBuilder.build(),
            notificationID
        )

        Glide.with(context)
            .asBitmap()
            .load(icon)
            .into(notificationTarget)

    }

    private fun getNextInt() = AtomicInteger().incrementAndGet()
}