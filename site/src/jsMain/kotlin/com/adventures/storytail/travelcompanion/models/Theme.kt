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
    LightGray(
        hex = "#FAFAFA",
        rgb = rgb(250, 250, 250)
    ),

}