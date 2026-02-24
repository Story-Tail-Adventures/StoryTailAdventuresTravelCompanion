package com.adventures.storytail.travelcompanion.components.sections

import androidx.compose.runtime.Composable
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.styles.DestinationCardStyle
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY_BODY
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY_HEADING
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

private data class Destination(
    val name: String,
    val description: String,
    val colorR: Int,
    val colorG: Int,
    val colorB: Int
)

private val destinations = listOf(
    Destination("Alaska", "Glaciers & Wildlife", 30, 80, 120),
    Destination("Caribbean", "Sun & Sand", 0, 150, 180),
    Destination("Europe", "Culture & History", 100, 60, 40),
    Destination("Hawaii", "Paradise Islands", 20, 120, 80)
)

@Composable
fun DestinationsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 60.px, leftRight = 24.px)
            .backgroundColor(Colors.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Section heading
        SpanText(
            text = "Featured Destinations",
            modifier = Modifier
                .fontFamily(FONT_FAMILY_HEADING)
                .fontSize(2.2.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(Theme.DarkCharcoal.rgb)
                .textAlign(TextAlign.Center)
                .margin(bottom = 12.px)
        )

        // Subtitle
        SpanText(
            text = "Explore our most popular travel destinations",
            modifier = Modifier
                .fontFamily(FONT_FAMILY_BODY)
                .fontSize(1.cssRem)
                .color(Theme.TextGray.rgb)
                .textAlign(TextAlign.Center)
                .margin(bottom = 40.px)
        )

        // Destinations grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(1200.px)
                .flexWrap(FlexWrap.Wrap)
                .gap(20.px),
            horizontalArrangement = com.varabyte.kobweb.compose.foundation.layout.Arrangement.Center
        ) {
            destinations.forEach { destination ->
                DestinationCard(destination)
            }
        }
    }
}

@Composable
private fun DestinationCard(destination: Destination) {
    Box(
        modifier = DestinationCardStyle.toModifier()
            .minWidth(250.px)
            .maxWidth(280.px)
            .height(320.px)
            .borderRadius(8.px)
            .backgroundColor(rgba(destination.colorR, destination.colorG, destination.colorB, 0.9))
            .cursor(Cursor.Pointer)
            .onClick { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpanText(
                text = destination.name,
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_HEADING)
                    .fontSize(1.8.cssRem)
                    .fontWeight(FontWeight.Bold)
                    .color(Colors.White)
                    .textAlign(TextAlign.Center)
                    .margin(bottom = 8.px)
            )
            SpanText(
                text = destination.description,
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_BODY)
                    .fontSize(1.cssRem)
                    .color(rgba(255, 255, 255, 0.8))
                    .textAlign(TextAlign.Center)
                    .margin(bottom = 20.px)
            )
            SpanText(
                text = "Discover More",
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_BODY)
                    .fontSize(14.px)
                    .fontWeight(FontWeight.SemiBold)
                    .color(Theme.LightBlue.rgb)
            )
        }
    }
}
