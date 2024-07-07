package com.vodimobile.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
)

@Composable
fun VodimobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    val Typography = Typography(
        headlineLarge = TextStyle(
            fontSize = 20.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Normal,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),
        headlineMedium = TextStyle(
            fontSize = 18.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),
        headlineSmall = TextStyle(
            fontSize = 16.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Medium,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),

        titleLarge = TextStyle(
            fontSize = 15.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Normal,
            lineHeight = 0.04.sp,
            letterSpacing = 0.05.sp
        ),

        bodyLarge = TextStyle(
            fontSize = 18.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Medium,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Normal,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Light,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),

        labelMedium = TextStyle(
            fontSize = 14.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Normal,
            lineHeight = 0.03.sp,
            letterSpacing = 0.05.sp
        ),
        labelSmall = TextStyle(
            fontSize = 12.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Light,
            lineHeight = 0.05.sp,
            letterSpacing = 0.05.sp
        ),

        displaySmall = TextStyle(
            fontSize = 12.sp,
            fontFamily = getMainFont(),
            fontWeight = FontWeight.Medium,
            lineHeight = 0.03.sp,
            letterSpacing = 0.05.sp
        )
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}