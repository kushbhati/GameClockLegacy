package com.neuraldischarge.gameclock.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.neuraldischarge.gameclock.clockmodules.ClockFace
import com.neuraldischarge.gameclock.clockmodules.MultiTimerHandler
import com.neuraldischarge.gameclock.dataclasses.MultiTimerHandlerConfig

// activity responsible for showing clock screens
class ClockActivity : AppCompatActivity() {

    // references to the ClockFace and ClockHandler
    private lateinit var clockFace: ClockFace
    private lateinit var multiTimerHandler: MultiTimerHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get required information and configurations from the intent
        @Suppress("DEPRECATION")
        clockFace = intent.getSerializableExtra("clock_face_name") as ClockFace

        @Suppress("DEPRECATION")
        val multiTimerHandlerConfig =
            intent.getSerializableExtra("clock_config") as MultiTimerHandlerConfig

        // initialising the objects
        multiTimerHandler = MultiTimerHandler(multiTimerHandlerConfig, clockFace)

        // passing required references to the objects
        clockFace.handler = multiTimerHandler

        // finally setting up the UI
        setContent {
            clockFace.CreateUI()
        }
    }
}