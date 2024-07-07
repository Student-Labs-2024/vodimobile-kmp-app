package com.vodimobile.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.vodimobile.resources.Res
import com.vodimobile.resources.akzidenzgroteskpro_regular
import org.jetbrains.compose.resources.Font

@Composable
fun getMainFont() : FontFamily =
    FontFamily(Font(resource = Res.font.akzidenzgroteskpro_regular))