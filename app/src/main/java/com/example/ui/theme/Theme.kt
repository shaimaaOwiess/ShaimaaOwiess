package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
  lightColorScheme(
    primary = LavenderPrimary,
    onPrimary = Color.White,
    primaryContainer = LavenderLight,
    onPrimaryContainer = LavenderDark,
    secondary = CampfireOrange,
    onSecondary = Color.White,
    secondaryContainer = CampfireGlow,
    onSecondaryContainer = CampfireAmber,
    tertiary = DinoMint,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE8F5E9),
    onTertiaryContainer = ForestGreen,
    background = PrehistoricPaper,
    onBackground = PrehistoricTextPrimary,
    surface = PrehistoricCard,
    onSurface = PrehistoricTextPrimary,
    surfaceVariant = StoneWarm,
    onSurfaceVariant = PrehistoricTextSecondary,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = LavenderSecondary,
    onPrimary = Color.Black,
    primaryContainer = LavenderDark,
    onPrimaryContainer = LavenderLight,
    secondary = CampfireAmber,
    onSecondary = Color.Black,
    tertiary = DinoMint,
    background = Color(0xFF1F1B1A),
    surface = Color(0xFF2B2625),
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
