package com.neuraldischarge.gameclock.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.neuraldischarge.gameclock.dataclasses.DisplayConfig
import com.neuraldischarge.gameclock.datastore.SavedConfigsManager
import com.neuraldischarge.gameclock.ui.layout.ListerActivityLayout

class ListerActivity : AppCompatActivity() {

    private var configArray: MutableState<Array<DisplayConfig>?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListerActivityLayout.CreateLayout(configArray.value)
        }

    }

    override fun onResume() {
        super.onResume()

        configArray.value = SavedConfigsManager.getConfigArray()

    }

}

