package com.neuraldischarge.gameclock.datastore

import com.neuraldischarge.gameclock.dataclasses.DisplayConfig
import com.neuraldischarge.gameclock.dataclasses.Preferences
import com.neuraldischarge.gameclock.datastore.defaultdata.DefaultConfigs
import com.neuraldischarge.gameclock.datastore.defaultdata.DefaultPreferences
import java.io.*

object PreferencesManager {

    var filePathName = ""

    private fun Preferences.fillNulls(preferences: Preferences): Preferences {
        return Preferences(
            clockFace = clockFace ?: preferences.clockFace,
            colorScheme = colorScheme ?: preferences.colorScheme
        )
    }

    fun getPreferences(): Preferences {
        val fis = FileInputStream(filePathName)
        val ois = ObjectInputStream(fis)

        @Suppress("UNCHECKED_CAST")
        val obj = ois.readObject() as Preferences

        fis.close()
        return obj
    }

    fun setDefaultData() {
        val data = DefaultPreferences.preferences
        File(filePathName).delete()
        val fos = FileOutputStream(filePathName)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(data)
        fos.close()
    }

    fun setPreferences(preferences: Preferences) {
        val obj = preferences.fillNulls(getPreferences())
        File(filePathName).delete()
        val fos = FileOutputStream(filePathName)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(obj)
        fos.close()
    }

}