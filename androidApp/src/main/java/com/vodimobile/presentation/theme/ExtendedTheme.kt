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
    val containerBack: Color,
    val secondaryBackground: Color,
    val onSecondaryBackground: Color,
    val hintText: Color,
    val smsTextFieldBack: Color,
    val smsTextFieldBorder: Color,
    val approvedOrder: Color,
    val processingOrder: Color,
    val cancelledOrder: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        containerBack = Color.Unspecified,
        secondaryBackground = Color.Unspecified,
        onSecondaryBackground = Color.Unspecified,
        hintText = Color.Unspecified,
        smsTextFieldBack = Color.Unspecified,
        smsTextFieldBorder = Color.Unspecified,
        approvedOrder = Color.Unspecified,
        processingOrder = Color.Unspecified,
        cancelledOrder = Color.Unspecified
    )
}

@Composable
fun ExtendedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val extendedColors = if (darkTheme)
        ExtendedColors(
            containerBack = onSecondaryBackgroundDark,
            secondaryBackground = secondaryBackgroundDark,
            onSecondaryBackground = onSecondaryBackgroundDark,
            hintText = hintTextDark,
            smsTextFieldBack = smsTextFieldBackDark,
            smsTextFieldBorder = smsTextFieldBorderDark,
            approvedOrder = greenDark,
            processingOrder = yellowLDark,
            cancelledOrder = pinkDark

        )
    else
        ExtendedColors(
            containerBack = containerBackLight,
            secondaryBackground = secondaryBackgroundLight,
            onSecondaryBackground = onSecondaryBackgroundLight,
            hintText = hintTextLight,
            smsTextFieldBack = smsTextFieldBackLight,
            smsTextFieldBorder = smsTextFieldBorderLight,
            approvedOrder = greenLight,
            processingOrder = yellowLight,
            cancelledOrder = pinkLight

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
