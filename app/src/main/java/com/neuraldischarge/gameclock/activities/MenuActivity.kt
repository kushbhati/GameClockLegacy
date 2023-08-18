package com.neuraldischarge.gameclock.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.neuraldischarge.gameclock.ui.layout.MenuActivityLayout

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuActivityLayout.CreateLayout(this)
        }
    }
}