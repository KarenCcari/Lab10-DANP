package com.example.laboratorio10danp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class Evento2(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object{
        const val CHANNEL_ID="channer_id"
        const val NOTIFICATION=1
    }

    val TAG = "Myperiodicwork"
    override fun doWork(): Result {
        Log.d("Do work2","Si funciona2")
        showNotif()
        return Result.success()
    }
    private fun showNotif() {
        val intent= Intent(applicationContext,MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent=PendingIntent.getActivity(applicationContext,0,intent,0)
        val notification=Notification.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Notificacion2")
            .setContentText("Esta es otra notificacion para probar paralelismo")
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            val channelName="channel name"
            val channelDescription= "Channel Description"
            val channelImportance=NotificationManager.IMPORTANCE_HIGH
            val channel=NotificationChannel(CHANNEL_ID,channelName,channelImportance).apply {
                description=channelDescription
            }
            val notificationManager= applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            with(NotificationManagerCompat.from(applicationContext)){
                notify(NOTIFICATION,notification.build())
            }
        }


    }




}

