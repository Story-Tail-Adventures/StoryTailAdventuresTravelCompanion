package com.adventures.storytail.travelcompanion

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.KobwebApp
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.jetbrains.compose.web.css.vh
import org.w3c.dom.HTMLLinkElement

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        loadGoogleFonts()
    }

    SilkApp {
        Surface(SmoothColorStyle.toModifier().minHeight(100.vh)){
            content()
        }
    }
}

private fun loadGoogleFonts() {
    val head = document.head ?: return

    // Preconnect to Google Fonts
    (document.createElement("link") as HTMLLinkElement).apply {
        rel = "preconnect"
        href = "https://fonts.googleapis.com"
        head.appendChild(this)
    }
    (document.createElement("link") as HTMLLinkElement).apply {
        rel = "preconnect"
        href = "https://fonts.gstatic.com"
        setAttribute("crossorigin", "")
        head.appendChild(this)
    }

    // Load Raleway and Mulish fonts
    (document.createElement("link") as HTMLLinkElement).apply {
        rel = "stylesheet"
        href = "https://fonts.googleapis.com/css2?family=Raleway:wght@300;400;500;600;700;900&family=Mulish:wght@300;400;600;700&display=swap"
        head.appendChild(this)
    }
}
