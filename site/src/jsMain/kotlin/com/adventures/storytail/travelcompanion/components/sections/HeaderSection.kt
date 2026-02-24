package com.adventures.storytail.travelcompanion.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.styles.NavLinkStyle
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY_BODY
import com.adventures.storytail.travelcompanion.util.Res
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px

private val navItems = listOf(
    "About Us",
    "Locations",
    "Self Service",
    "Store",
    "Adventure Logs",
    "Contact Us"
)

@Composable
fun HeaderSection() {
    var mobileMenuOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Theme.DarkCharcoal.rgb),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth().maxWidth(1200.px)) {
            // Main nav bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(leftRight = 24.px, topBottom = 16.px),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Logo
                Link(path = "/") {
                    Image(
                        modifier = Modifier.height(50.px),
                        src = Res.Image.logo,
                        description = "Story Tail Adventures Logo"
                    )
                }

                // Desktop nav links
                Row(
                    modifier = Modifier.gap(24.px),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEach { item ->
                        Link(
                            path = "#",
                            modifier = NavLinkStyle.toModifier()
                                .fontFamily(FONT_FAMILY_BODY)
                                .fontSize(14.px)
                                .fontWeight(FontWeight.SemiBold)
                                .textDecorationLine(TextDecorationLine.None)
                        ) {
                            com.varabyte.kobweb.silk.components.text.SpanText(item)
                        }
                    }
                }

                // Mobile hamburger
                Box(
                    modifier = Modifier
                        .display(DisplayStyle.None)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick { mobileMenuOpen = !mobileMenuOpen }
                ) {
                    FaBars(size = IconSize.LG)
                }
            }

            // Mobile dropdown menu
            if (mobileMenuOpen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(leftRight = 24.px, bottom = 16.px)
                        .gap(12.px)
                ) {
                    navItems.forEach { item ->
                        Link(
                            path = "#",
                            modifier = NavLinkStyle.toModifier()
                                .fontFamily(FONT_FAMILY_BODY)
                                .fontSize(14.px)
                                .fontWeight(FontWeight.SemiBold)
                                .textDecorationLine(TextDecorationLine.None)
                        ) {
                            com.varabyte.kobweb.silk.components.text.SpanText(item)
                        }
                    }
                }
            }
        }
    }
}
