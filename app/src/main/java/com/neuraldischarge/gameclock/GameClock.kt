package com.neuraldischarge.gameclock

import android.app.Application
import com.neuraldischarge.gameclock.datastore.PreferencesManager
import com.neuraldischarge.gameclock.datastore.SavedConfigsManager

// Application File
class GameClock : Application() {

    override fun onCreate() {
        super.onCreate()

        // first thing the app does
        // 1. Initialise the filePathName of SavedConfigsManager
        PreferencesManager.filePathName = "$filesDir/preferences"
        SavedConfigsManager.filePathName = "$filesDir/configurations"
        PreferencesManager.setDefaultData()
        SavedConfigsManager.setDefaultData()
    }

}