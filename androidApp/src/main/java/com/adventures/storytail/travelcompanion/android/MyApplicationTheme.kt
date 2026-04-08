package com.adventures.storytail.travelcompanion.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFE8A68C),
            onPrimary = Color(0xFF4A0E0E),
            primaryContainer = Color(0xFF5B0E0E),
            onPrimaryContainer = Color(0xFFFFDAD4),
            secondary = Color(0xFFFFBB6B),
            onSecondary = Color(0xFF4A2800),
            secondaryContainer = Color(0xFF6B3D00),
            onSecondaryContainer = Color(0xFFFFDDB5),
            tertiary = Color(0xFFD4BFA8),
            background = Color(0xFF1A1110),
            onBackground = Color(0xFFF5DFDA),
            surface = Color(0xFF1A1110),
            onSurface = Color(0xFFF5DFDA),
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF7B1A1A),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFFFDAD4),
            onPrimaryContainer = Color(0xFF3B0907),
            secondary = Color(0xFFF6931D),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFF0E0),
            onSecondaryContainer = Color(0xFF5D3A00),
            tertiary = Color(0xFF6B5D55),
            background = Color(0xFFFFFAF5),
            onBackground = Color(0xFF2D2926),
            surface = Color(0xFFFFFAF5),
            onSurface = Color(0xFF2D2926),
        )
    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
