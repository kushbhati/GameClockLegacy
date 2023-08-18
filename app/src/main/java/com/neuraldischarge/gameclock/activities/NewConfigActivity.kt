package com.neuraldischarge.gameclock.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import com.neuraldischarge.gameclock.dataclasses.*
import com.neuraldischarge.gameclock.datastore.SavedConfigsManager
import com.neuraldischarge.gameclock.methods.VariantConverter.toNormalVariant
import com.neuraldischarge.gameclock.ui.layout.NewConfigActivityLayout

class NewConfigActivity : AppCompatActivity() {

    private var players = 0

    private val name = mutableStateOf("")
    private val desc = mutableStateOf("")
    private val icon = mutableStateOf("1")

    private lateinit var values: List<MutableList<StageConfigUIVariant>>

    fun saveNewConfig() {
        val config = DisplayConfig(
            title = name.value,
            desc = desc.value,
            icon = icon.value,
            value = MultiTimerHandlerConfig(
                players = players,
                clocks = values.map { i -> i.map { it.toNormalVariant() } }
            )
        )


        SavedConfigsManager.appendToSavedConfigArray(config)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        players = 2

        values =
            List(players) {
                mutableListOf(
                    StageConfigUIVariant()
                )
            }

        setContent {
            NewConfigActivityLayout.CreateLayout(
                name = name,
                desc = desc,
                icon = icon,
                values = values
            )
        }
    }
}


