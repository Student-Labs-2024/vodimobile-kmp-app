package com.vodimobile.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(15.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Immutable
data class ReplacementShapes(
    val extraSmall: Shape,
    val small: Shape,
    val medium: Shape,
    val large: Shape,
    val normal: Shape,
    val extraLarge: Shape,
)

val LocalReplacementShapes = staticCompositionLocalOf {
    ReplacementShapes(
        extraSmall = RoundedCornerShape(ZeroCornerSize),
        small = RoundedCornerShape(ZeroCornerSize),
        medium = RoundedCornerShape(ZeroCornerSize),
        large = RoundedCornerShape(ZeroCornerSize),
        normal = RoundedCornerShape(ZeroCornerSize),
        extraLarge = RoundedCornerShape(ZeroCornerSize)
    )
}
