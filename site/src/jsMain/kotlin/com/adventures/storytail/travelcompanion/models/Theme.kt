package com.adventures.storytail.travelcompanion.models

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb


enum class Theme(
    val hex: String,
    val rgb: CSSColorValue,
    val darkHex: String,
    val darkRgb: CSSColorValue
) {
    Primary(
        hex = "#7B1A1A",
        rgb = rgb(123, 26, 26),
        darkHex = "#E8872A",
        darkRgb = rgb(232, 135, 42)
    ),
    PrimaryDark(
        hex = "#5B0E0E",
        rgb = rgb(91, 14, 14),
        darkHex = "#C46A1A",
        darkRgb = rgb(196, 106, 26)
    ),
    Secondary(
        hex = "#F6931D",
        rgb = rgb(246, 147, 29),
        darkHex = "#3B8ED6",
        darkRgb = rgb(59, 142, 214)
    ),
    SecondaryLight(
        hex = "#FFF0E0",
        rgb = rgb(255, 240, 224),
        darkHex = "#1A2A3A",
        darkRgb = rgb(26, 42, 58)
    ),
    DarkCharcoal(
        hex = "#2D2926",
        rgb = rgb(45, 41, 38),
        darkHex = "#F0EDE8",
        darkRgb = rgb(240, 237, 232)
    ),
    White(
        hex = "#FFFFFF",
        rgb = rgb(255, 255, 255),
        darkHex = "#1A1A1A",
        darkRgb = rgb(26, 26, 26)
    ),
    Background(
        hex = "#FFFAF5",
        rgb = rgb(255, 250, 245),
        darkHex = "#121212",
        darkRgb = rgb(18, 18, 18)
    ),
    TextGray(
        hex = "#6B5D55",
        rgb = rgb(107, 93, 85),
        darkHex = "#A0B0B8",
        darkRgb = rgb(160, 176, 184)
    ),
    TextDarkGray(
        hex = "#8B7D75",
        rgb = rgb(139, 125, 117),
        darkHex = "#8899A4",
        darkRgb = rgb(136, 153, 164)
    ),
    LightGray(
        hex = "#FFF8F2",
        rgb = rgb(255, 248, 242),
        darkHex = "#1E2328",
        darkRgb = rgb(30, 35, 40)
    ),
}

@Composable
fun Theme.toColorMode(): CSSColorValue {
    return if (ColorMode.current == ColorMode.LIGHT) rgb else darkRgb
}

fun Theme.forColorMode(colorMode: ColorMode): CSSColorValue {
    return if (colorMode == ColorMode.LIGHT) rgb else darkRgb
}
