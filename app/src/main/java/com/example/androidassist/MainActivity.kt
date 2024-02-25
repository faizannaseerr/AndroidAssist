package com.example.androidassist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private var dateDisplay: TextView? = null
    private lateinit var batteryDisplay: ProgressBar
    private var batteryProgressStatus = 0
    private lateinit var mContext: Context


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      hide status bar...need to figure out how to do this for the whole app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())

        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        initTimeAndDate()
        initBattery()
    }

    @RequiresApi(Build.VERSION_CODES.O) //requires api 26
    fun initTimeAndDate() {
        //time and date
        dateDisplay = findViewById(R.id.tv_date)
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        dateDisplay?.let {
            it.text = currentDate
        }
    }

    private fun initBattery() {
        //battery
        mContext = applicationContext
        var iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        mContext.registerReceiver(batteryBroadcastReceiver, iFilter)
        batteryDisplay = findViewById(R.id.pb_battery)

    }

    private val batteryBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val percentage = level.toFloat() / scale
            batteryProgressStatus = (percentage * 100).toInt()
            if (batteryProgressStatus <= 25){
                batteryDisplay.progressTintList = android.content.res.ColorStateList.valueOf(Color.RED);
            } else {
                batteryDisplay.progressTintList = android.content.res.ColorStateList.valueOf(Color.GREEN);
            }
            batteryDisplay.progress = batteryProgressStatus
        }
    }
}