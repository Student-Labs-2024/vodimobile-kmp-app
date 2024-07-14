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
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        headerBack = Color.Unspecified,
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
        )
    else
        ExtendedColors(
            headerBack = headerBackLight
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