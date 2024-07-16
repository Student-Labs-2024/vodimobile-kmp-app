package com.vodimobile.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vodimobile.android.R

val mainFont : FontFamily =
    FontFamily(Font(resId = R.font.akzidenzgroteskpro_regular))

val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.05.sp
    ),
    headlineMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.05.sp
    ),
    headlineSmall = TextStyle(
        fontSize = 16.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.05.sp
    ),

    titleLarge = TextStyle(
        fontSize = 15.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.05.sp
    ),

    bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.05.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.05.sp
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Light,
        letterSpacing = 0.05.sp
    ),

    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.05.sp
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Light,
        letterSpacing = 0.05.sp
    ),

    displaySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.05.sp
    )
)