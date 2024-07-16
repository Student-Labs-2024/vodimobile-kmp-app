package com.vodimobile.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val headerBack: Color,
    val secondaryBackground: Color,
    val onSecondaryBackground: Color,
    val hintText: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        headerBack = Color.Unspecified,
        secondaryBackground = Color.Unspecified,
        onSecondaryBackground = Color.Unspecified,
        hintText = Color.Unspecified
    )
}

@Composable
fun ExtendedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val extendedColors = if (darkTheme)
        ExtendedColors(
            headerBack = surfaceVariantDark,
            secondaryBackground = secondaryBackgroundDark,
            onSecondaryBackground = onSecondaryBackgroundDark,
            hintText = hintTextDark
        )
    else
        ExtendedColors(
            headerBack = headerBackLight,
            secondaryBackground = secondaryBackgroundLight,
            onSecondaryBackground = onSecondaryBackgroundLight,
            hintText = hintTextLight
        )

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            content = content
        )
    }
}

object ExtendedTheme {
    val colorScheme: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}