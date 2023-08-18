package com.neuraldischarge.gameclock.dataclasses

import com.neuraldischarge.gameclock.clockmodules.ClockFace
import com.neuraldischarge.gameclock.ui.themes.ColorSchemeExtended
import java.io.Serializable

data class Preferences(
    val clockFace: ClockFace? = null,
    val colorScheme: ColorSchemeExtended? = null
) : Serializable
