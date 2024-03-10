package com.example.androidassist.sharedComponents.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo

abstract class BaseApps : AppCompatActivity() {
    protected lateinit var appHeaderIcon: ImageView
    protected lateinit var appHeaderTitle: TextView
    protected lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_apps)

        appHeaderIcon = findViewById(R.id.appHeaderIcon)
        appHeaderTitle = findViewById(R.id.appHeaderTitle)
        backButton = findViewById(R.id.backButton)

        hideStatusBar()
        setupHeader()
        setupFragment()
        setupBackButton()
        setupStyles()
    }

    abstract val appInfo: AppsInfo

    abstract fun setupFragment()

    abstract fun setupBackButton()

    fun replaceFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, newFragment).commit()
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupHeader() {
        this.appHeaderIcon.setImageResource(appInfo.imageResource)
        this.appHeaderTitle.text = appInfo.appName
    }

    private fun setupStyles() {

    }
}