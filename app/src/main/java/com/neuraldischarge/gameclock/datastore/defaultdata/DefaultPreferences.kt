package com.neuraldischarge.gameclock.datastore.defaultdata

import com.neuraldischarge.gameclock.clockmodules.ClockFace
import com.neuraldischarge.gameclock.dataclasses.Preferences
import com.neuraldischarge.gameclock.ui.themes.ColorSchemeExtended

object DefaultPreferences {
    val preferences = Preferences(
        clockFace = ClockFace.DUAL_BUTTONED,
        colorScheme = ColorSchemeExtended.DEFAULT
    )
}