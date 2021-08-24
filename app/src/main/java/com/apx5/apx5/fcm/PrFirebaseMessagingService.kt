package com.apx5.apx5.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import javax.inject.Inject

/**
 * FireBase
 */

class PrFirebaseMessagingService : FirebaseMessagingService() {

    @Inject lateinit var prPreference: PrPreference

    private val fcmData: HashMap<String, String> = hashMapOf()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data

        val myTeamCode = prPreference.userTeam?: ""
        val userEmail = prPreference.userEmail?: ""

        messageKeys.forEach {
            fcmData[it] = data[it].toString()
        }

        fcmData["myTeam"] = myTeamCode
        fcmData["userEmail"] = userEmail

        if (prPreference.getBoolean(PrPrefKeys.NOTIFICATION, true)) sendNotification(fcmData)
    }

    /* 알림발송*/
    private fun sendNotification(fcmData: HashMap<*, *>) {
        val title = fcmData[PrConstants.Fcm.TITLE].toString()

        /* 메시지 형식 : 07월01일 - 기아(4) vs 두산(5) */
        val fcmMsg = String.format(resources.getString(R.string.notification_format),
            fcmData[PrConstants.Fcm.DATE].toString(),
            PrTeam.team(fcmData[PrConstants.Fcm.AWAY_CODE].toString()).abbrName,
            PrTeam.team(fcmData[PrConstants.Fcm.HOME_CODE].toString()).abbrName,
            fcmData[PrConstants.Fcm.AWAY_SCORE].toString(),
            fcmData[PrConstants.Fcm.HOME_SCORE].toString()
        )

        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.setBigContentTitle(title)
        bigTextStyle.bigText(fcmMsg)

        val channelId = "Prooya Result"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.prooya_logo)
            .setContentTitle(title)
            .setStyle(bigTextStyle)
            .setContentText(fcmMsg)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Prooya", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.cancelAll()
        notificationManager.notify(1, notificationBuilder.build())
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }


    companion object {
        /* 메세지 데이터 키*/
        private val messageKeys = listOf(
            PrConstants.Fcm.TITLE,
            PrConstants.Fcm.DATE,
            PrConstants.Fcm.AWAY_CODE,
            PrConstants.Fcm.HOME_CODE,
            PrConstants.Fcm.AWAY_SCORE,
            PrConstants.Fcm.HOME_SCORE
        )
    }
}