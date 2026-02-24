package com.adventures.storytail.travelcompanion.styles

import com.adventures.storytail.travelcompanion.models.Theme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.ms

val NavLinkStyle by ComponentStyle {
    base {
        Modifier
            .color(Colors.White)
            .transition(CSSTransition(property = "color", duration = 300.ms))
    }
    hover {
        Modifier.color(Theme.AccentBlue.rgb)
    }
}

val HeroCTAButtonStyle by ComponentStyle {
    base {
        Modifier
            .backgroundColor(Theme.LightBlue.rgb)
            .color(Theme.AccentBlue.rgb)
            .transition(
                CSSTransition(property = "transform", duration = 300.ms),
                CSSTransition(property = "background-color", duration = 300.ms)
            )
    }
    hover {
        Modifier
            .backgroundColor(Theme.AccentBlue.rgb)
            .color(Colors.White)
    }
}

val DestinationCardStyle by ComponentStyle {
    base {
        Modifier
            .transition(
                CSSTransition(property = "transform", duration = 300.ms),
                CSSTransition(property = "box-shadow", duration = 300.ms)
            )
    }
    hover {
        Modifier.scale(1.03)
    }
}

val FooterLinkStyle by ComponentStyle {
    base {
        Modifier
            .color(Theme.TextGray.rgb)
            .transition(CSSTransition(property = "color", duration = 300.ms))
    }
    hover {
        Modifier.color(Colors.White)
    }
}
