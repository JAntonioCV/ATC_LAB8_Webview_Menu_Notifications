package com.jantonioc.lab8androidatc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    lateinit var btnGo: Button
    lateinit var etUrl: TextView
    lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUrl = findViewById(R.id.etURL)
        btnGo = findViewById(R.id.btnGo)
        webView = findViewById(R.id.webView)

        btnGo.setOnClickListener {
            webView.setBackgroundColor(Color.TRANSPARENT)
            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webView.settings.javaScriptEnabled = true
            webView.settings.loadsImagesAutomatically= true
            webView.webViewClient = WebViewClient()
            val url = etUrl.text.toString()
            webView.loadUrl(url)
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        //variasbles para el canal
        val CHANNEL_ID = "myChannel_id_01"
        val CHANNEL_NAME = "Nombre del canal"
        val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        val chanelDescription = "Descripcion del canal"

        val  channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,CHANNEL_IMPORTANCE)
        channel.description = chanelDescription
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lightColor = getColor(R.color.purple_500)
        //crear la noficacion
        //2.1 generar pending intent
        val stackBuilder = TaskStackBuilder.create(this)

        //2.2creanis un inten
        val intent = Intent(this,SecondActivity::class.java)

        //2.2 agregar inten al tope de la pila virtual
        stackBuilder.addNextIntent(intent)

        //2.3

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Titulo de la notificacion")
            .setContentText("Mensaje de la notificacion")
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(10,notification.build())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.menuItem1 -> {
                webView.webViewClient = WebViewClient()
                webView.loadUrl("https://www.androidatc.com")
                showNotification()
                return super.onOptionsItemSelected(item)
            }

            R.id.menuItem2 -> {
                webView.webViewClient = WebViewClient()
                webView.loadUrl("https://www.pearsonvue.com/androidatc")
                return super.onOptionsItemSelected(item)
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}