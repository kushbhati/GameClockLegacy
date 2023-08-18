package com.neuraldischarge.gameclock.ui.themes

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import hct.Hct
import java.io.Serializable

enum class ColorCollection(val color: Int) {
    BLUE(color = 0x03A9F4),
    RED(color = 0x03A9F4),
    GREEN(color = 0x03A9F4);

    fun getColorAtTone(desiredTone: Double): Color {
        return Color(Hct.fromInt(color).apply { tone = desiredTone }.toInt())
    }
}

data class AdditionalColorScheme (
    val clockColor1: Color = Color.Transparent,
) : Serializable

sealed class ColorSchemeExtended : Serializable {

    abstract val lightColorScheme: ColorScheme
    abstract val darkColorScheme: ColorScheme
    abstract val additionalColorScheme: AdditionalColorScheme

    object DEFAULT : ColorSchemeExtended() {
        override val lightColorScheme: ColorScheme = lightColorScheme()
        override val darkColorScheme: ColorScheme = darkColorScheme()
        override val additionalColorScheme =
            AdditionalColorScheme(
                clockColor1 = ColorCollection.BLUE.getColorAtTone(60.0)
            )
    }

    object DYNAMIC : ColorSchemeExtended() {
        override val lightColorScheme: ColorScheme = lightColorScheme()
        override val darkColorScheme: ColorScheme = darkColorScheme()
        override var additionalColorScheme = AdditionalColorScheme()
        override fun getColorScheme(isDarkModeEnabled: Boolean, context: Context): ColorScheme {
            val colorScheme = if (isDarkModeEnabled) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            additionalColorScheme = AdditionalColorScheme(clockColor1 = Color(Hct.fromInt(colorScheme.primary.toArgb()).apply { tone = 60.0 }.toInt()))
            return colorScheme
        }
    }

    open fun getColorScheme(isDarkModeEnabled: Boolean, context: Context): ColorScheme {
        return if (isDarkModeEnabled) darkColorScheme else lightColorScheme
    }
}