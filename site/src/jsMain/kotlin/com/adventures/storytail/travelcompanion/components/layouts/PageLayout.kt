package com.adventures.storytail.travelcompanion.components.layouts

import androidx.compose.runtime.Composable
import com.adventures.storytail.travelcompanion.components.sections.FooterSection
import com.adventures.storytail.travelcompanion.components.sections.HeaderSection
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import org.jetbrains.compose.web.css.vh

@Composable
fun PageLayout(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().minHeight(100.vh)) {
        HeaderSection()
        Column(modifier = Modifier.fillMaxWidth().flexGrow(1)) {
            content()
        }
        FooterSection()
    }
}
