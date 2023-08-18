package com.neuraldischarge.gameclock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neuraldischarge.gameclock.activities.MenuActivity

// the activity visible to the launcher
class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}