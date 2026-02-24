package com.adventures.storytail.travelcompanion.components.sections

import androidx.compose.runtime.Composable
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.styles.FooterLinkStyle
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY_BODY
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.silk.components.icons.fa.FaFacebook
import com.varabyte.kobweb.silk.components.icons.fa.FaInstagram
import com.varabyte.kobweb.silk.components.icons.fa.FaTwitter
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun FooterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Theme.DarkCharcoal.rgb)
            .padding(topBottom = 40.px, leftRight = 24.px),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.maxWidth(1200.px).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Social media icons
            Row(
                modifier = Modifier
                    .gap(20.px)
                    .margin(bottom = 24.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Link(
                    path = "#",
                    modifier = FooterLinkStyle.toModifier()
                        .textDecorationLine(TextDecorationLine.None)
                ) {
                    FaFacebook(size = IconSize.LG)
                }
                Link(
                    path = "#",
                    modifier = FooterLinkStyle.toModifier()
                        .textDecorationLine(TextDecorationLine.None)
                ) {
                    FaInstagram(size = IconSize.LG)
                }
                Link(
                    path = "#",
                    modifier = FooterLinkStyle.toModifier()
                        .textDecorationLine(TextDecorationLine.None)
                ) {
                    FaTwitter(size = IconSize.LG)
                }
            }

            // Links
            Row(
                modifier = Modifier
                    .gap(24.px)
                    .margin(bottom = 20.px),
                horizontalArrangement = Arrangement.Center
            ) {
                Link(
                    path = "#",
                    modifier = FooterLinkStyle.toModifier()
                        .fontFamily(FONT_FAMILY_BODY)
                        .fontSize(14.px)
                        .textDecorationLine(TextDecorationLine.None)
                ) {
                    SpanText("Privacy Policy")
                }
                Link(
                    path = "#",
                    modifier = FooterLinkStyle.toModifier()
                        .fontFamily(FONT_FAMILY_BODY)
                        .fontSize(14.px)
                        .textDecorationLine(TextDecorationLine.None)
                ) {
                    SpanText("Contact Us")
                }
            }

            // Copyright
            SpanText(
                text = "\u00A9 2026 Story Tail Adventures. All rights reserved.",
                modifier = Modifier
                    .fontFamily(FONT_FAMILY_BODY)
                    .fontSize(13.px)
                    .color(Theme.TextDarkGray.rgb)
                    .textAlign(TextAlign.Center)
            )
        }
    }
}
