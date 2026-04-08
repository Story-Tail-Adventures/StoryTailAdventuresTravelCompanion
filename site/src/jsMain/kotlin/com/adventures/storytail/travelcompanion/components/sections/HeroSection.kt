package com.adventures.storytail.travelcompanion.components.sections

import androidx.compose.runtime.Composable
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.styles.HeroCTAButtonStyle
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY_BODY
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY_HEADING
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh

@Composable
fun HeroSection() {
    val heroOverlay = if (ColorMode.current.isLight) {
        rgba(123, 26, 26, 0.80)
    } else {
        rgba(18, 18, 18, 0.80)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(80.vh)
            .backgroundColor(heroOverlay),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 24.px)
                .gap(20.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main heading
            SpanText(
                text = "Making Travel",
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_HEADING)
                    .fontSize(3.5.cssRem)
                    .fontWeight(FontWeight.Bold)
                    .color(Colors.White)
                    .textAlign(TextAlign.Center)
            )
            SpanText(
                text = "An Adventure",
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_HEADING)
                    .fontSize(3.5.cssRem)
                    .fontWeight(FontWeight.Light)
                    .color(Colors.White)
                    .textAlign(TextAlign.Center)
            )

            // Subtitle
            SpanText(
                text = "Your trusted travel companion for unforgettable journeys",
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_BODY)
                    .fontSize(1.1.cssRem)
                    .color(rgba(255, 255, 255, 0.7))
                    .textAlign(TextAlign.Center)
                    .margin(top = 8.px)
            )

            // CTA Button
            Box(
                modifier = HeroCTAButtonStyle.toModifier()
                    .margin(top = 24.px)
                    .padding(leftRight = 40.px, topBottom = 14.px)
                    .borderRadius(4.px)
                    .fontFamily(FONT_FAMILY_BODY)
                    .fontSize(16.px)
                    .fontWeight(FontWeight.SemiBold)
                    .cursor(Cursor.Pointer)
                    .onClick { },
                contentAlignment = Alignment.Center
            ) {
                SpanText("Book Now")
            }
        }
    }
}
