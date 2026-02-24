package com.adventures.storytail.travelcompanion.pages

import androidx.compose.runtime.Composable
import com.adventures.storytail.travelcompanion.components.layouts.PageLayout
import com.adventures.storytail.travelcompanion.components.sections.DestinationsSection
import com.adventures.storytail.travelcompanion.components.sections.HeroSection
import com.varabyte.kobweb.core.Page

@Page
@Composable
fun HomePage() {
    PageLayout {
        HeroSection()
        DestinationsSection()
    }
}
