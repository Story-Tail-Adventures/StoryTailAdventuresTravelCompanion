package com.adventures.storytail.travelcompanion.models

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb


enum class Theme(
    val hex: String,
    val rgb: CSSColorValue
) {
    Primary(
        hex = "#F6931D",
        rgb = rgb(246, 147, 29)
    ),
    DarkCharcoal(
        hex = "#242625",
        rgb = rgb(36, 38, 37)
    ),
    AccentBlue(
        hex = "#00AFE9",
        rgb = rgb(0, 175, 233)
    ),
    LightBlue(
        hex = "#E5F8FC",
        rgb = rgb(229, 248, 252)
    ),
    White(
        hex = "#FFFFFF",
        rgb = rgb(255, 255, 255)
    ),
    TextGray(
        hex = "#75939B",
        rgb = rgb(117, 147, 155)
    ),
    TextDarkGray(
        hex = "#82868B",
        rgb = rgb(130, 134, 139)
    ),
    LightGray(
        hex = "#FAFAFA",
        rgb = rgb(250, 250, 250)
    ),
}