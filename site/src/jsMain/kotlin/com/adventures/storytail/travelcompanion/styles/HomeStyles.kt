package com.adventures.storytail.travelcompanion.styles

import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.models.forColorMode
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.ms

val NavLinkStyle = CssStyle {
    base {
        Modifier
            .color(Theme.DarkCharcoal.forColorMode(colorMode))
            .transition(Transition.of("color", 300.ms))
    }
    hover {
        Modifier.color(Theme.Primary.forColorMode(colorMode))
    }
}

val HeroCTAButtonStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Theme.SecondaryLight.forColorMode(colorMode))
            .color(Theme.Primary.forColorMode(colorMode))
            .transition(
                Transition.of("transform", 300.ms),
                Transition.of("background-color", 300.ms)
            )
    }
    hover {
        Modifier
            .backgroundColor(Theme.Primary.forColorMode(colorMode))
            .color(Colors.White)
    }
}

val DestinationCardStyle = CssStyle {
    base {
        Modifier
            .transition(
                Transition.of("transform", 300.ms),
                Transition.of("box-shadow", 300.ms)
            )
    }
    hover {
        Modifier.scale(1.03)
    }
}

val FooterLinkStyle = CssStyle {
    base {
        Modifier
            .color(Theme.TextGray.forColorMode(colorMode))
            .transition(Transition.of("color", 300.ms))
    }
    hover {
        Modifier.color(Theme.Primary.forColorMode(colorMode))
    }
}
