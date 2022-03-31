package com.example.sample.entry

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.RemoteViews
import androidx.activity.viewModels
import com.example.sample.R

import com.example.sample.databinding.ActivityLoginBinding
import com.example.sample.viewmodel.AppViewModel
import com.example.sample.widget.CMSWidgetProvider


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeWidget()

    }

    private fun changeWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(packageName, R.layout.widget_cms)
        val thisWidget = ComponentName(this, CMSWidgetProvider::class.java)
        remoteViews.setViewVisibility(R.id.login,VISIBLE)

        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.action = System.currentTimeMillis().toString()
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val loginPendingIntent = PendingIntent.getActivity(
            this,
            0,
            loginIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setViewVisibility(R.id.student_layout, View.GONE)
        remoteViews.setViewVisibility(R.id.professor_layout, View.GONE)
        remoteViews.setViewVisibility(R.id.admin_layout, View.GONE)
        remoteViews.setViewVisibility(R.id.no_of_professors_layout,View.GONE)
        remoteViews.setViewVisibility(R.id.no_of_student_layout,View.GONE)

        remoteViews.setOnClickPendingIntent(
            R.id.login_button,
            loginPendingIntent
        )
        appWidgetManager?.updateAppWidget(thisWidget, remoteViews)
    }


}