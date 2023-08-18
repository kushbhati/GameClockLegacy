package com.neuraldischarge.gameclock.datastore

import com.neuraldischarge.gameclock.dataclasses.DisplayConfig
import com.neuraldischarge.gameclock.datastore.defaultdata.DefaultConfigs
import java.io.*

// object that manages the storing of clock configuration data
object SavedConfigsManager {

    // path name of the configurations file
    // set when application initialises
    var filePathName = ""

    fun getConfigArray(): Array<DisplayConfig>{
        val fis = FileInputStream(filePathName)
        val ois = ObjectInputStream(fis)

        @Suppress("UNCHECKED_CAST")
        val obj = ois.readObject() as Array<DisplayConfig>

        fis.close()
        return obj
    }

    fun overwriteConfigArray(data: Array<DisplayConfig>) {
        deleteSavedData()
        val fos = FileOutputStream(filePathName)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(data)
        fos.close()
    }

    fun deleteSavedData() {
        File(filePathName).delete()
    }

    fun setDefaultData() {
        val data = DefaultConfigs.getConfigs()
        overwriteConfigArray(data)
    }

    fun appendToSavedConfigArray(config: DisplayConfig) {
        val obj = getConfigArray()
        overwriteConfigArray(obj.plus(config))
    }
}