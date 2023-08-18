package com.neuraldischarge.gameclock.ui.themes

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.neuraldischarge.gameclock.datastore.PreferencesManager


class DynamicThemeScope(val colorSchemeExtended: ColorSchemeExtended) {
    @Composable
    fun Content(content: @Composable DynamicThemeScope.() -> Unit) { content() }
}

@Composable
fun DynamicTheme(
    isDarkModeEnabled: Boolean = isSystemInDarkTheme(),
    isFullScreen: Boolean = true,
    content: @Composable DynamicThemeScope.() -> Unit
) {

    val context = LocalContext.current
    val view = LocalView.current

    val colorSchemeExtended = PreferencesManager.getPreferences().colorScheme ?: ColorSchemeExtended.DEFAULT
    val colorScheme = colorSchemeExtended.getColorScheme(isDarkModeEnabled, context)

    SideEffect {
        val window = (context as Activity).window
        val background = if (isFullScreen) Color.Transparent else colorScheme.surface

        window.statusBarColor = background.toArgb()
        window.navigationBarColor = background.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkModeEnabled
        WindowCompat.setDecorFitsSystemWindows(window, !isFullScreen)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography()
    ) {
        DynamicThemeScope(colorSchemeExtended = colorSchemeExtended).Content(content = content)
    }
}