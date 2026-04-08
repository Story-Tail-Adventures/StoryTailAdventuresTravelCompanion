package com.adventures.storytail.travelcompanion.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.models.toColorMode
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.adventures.storytail.travelcompanion.util.Res
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input

// ── Accent palette — semantic colors that don't change with dark/light mode ──
private val PrimaryMaroon = rgb(123, 26, 26)   // maroon primary for icon badges
private val GreenAccent  = rgb(16, 185, 129)   // positive trend
private val OrangeAccent = rgb(246, 147,  29)  // secondary accent
private val RedAccent    = rgb(239,  68,  68)  // negative trend / done
private val PurpleAccent = rgb(139,  92, 246)  // revenue accent
private val TealAccent   = rgb(20,  184, 166)  // deals accent

// ── Data models ───────────────────────────────────────────────────────────────
private data class SidebarSection(val title: String, val items: List<NavItem>)
private data class NavItem(
    val icon: String,
    val label: String,
    val isActive: Boolean = false,
    val badge: String? = null
)
private data class StatCardData(
    val title: String,
    val value: String,
    val icon: String,
    val iconBg: CSSColorValue,
    val trendText: String,
    val trendColor: CSSColorValue
)
private data class TaskItemData(
    val text: String,
    val done: Boolean = false,
    val badge: String? = null,
    val badgeColor: CSSColorValue? = null
)

// ── Sample data ───────────────────────────────────────────────────────────────
private val sidebarSections = listOf(
    SidebarSection("MAIN", listOf(
        NavItem("⊞",  "Dashboard",    isActive = true),
        NavItem("🌍", "Destinations"),
        NavItem("🏷", "Travel Deals", badge = "New"),
        NavItem("👥", "Testimonials"),
        NavItem("📅", "Bookings"),
    )),
    SidebarSection("CONTENT", listOf(
        NavItem("✏",  "Blog Posts"),
        NavItem("🖼", "Media Library"),
        NavItem("💬", "Comments"),
    )),
    SidebarSection("SYSTEM", listOf(
        NavItem("⚙",  "Settings"),
        NavItem("👤", "Admins"),
    ))
)

private val statCards = listOf(
    StatCardData("TOTAL BOOKINGS",  "1,520",    "📋", PrimaryMaroon,      "↑ 12%  vs last month",  GreenAccent),
    StatCardData("ACTIVE DEALS",    "48",       "🏷", TealAccent,        "↑ 5  new this week",    GreenAccent),
    StatCardData("PENDING REVIEWS", "12",       "⭐", OrangeAccent,      "Action needed",          OrangeAccent),
    StatCardData("REVENUE (MAY)",   "\$135,965","📈", PurpleAccent,      "↓ 2.4%  vs last month", RedAccent),
)

private val sampleTasks = listOf(
    TaskItemData("Review \"Summer in Italy\" testimonials", badge = "High Priority", badgeColor = OrangeAccent),
    TaskItemData("Update pricing for Maldives package",     done = true),
    TaskItemData("Upload new photos for Swiss Alps",         badge = "Media",         badgeColor = PrimaryMaroon),
    TaskItemData("Draft blog post: \"Top 10 Winter Destinations\""),
)

// ── Page ──────────────────────────────────────────────────────────────────────
@Page
@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .minHeight(100.vh)
            .backgroundColor(Theme.Background.toColorMode())
    ) {
        TopNavBar()
        Row(modifier = Modifier.fillMaxWidth().flexGrow(1)) {
            AdminSidebar()
            Column(
                modifier = Modifier
                    .flexGrow(1)
                    .backgroundColor(Theme.Background.toColorMode())
                    .overflow(Overflow.Auto)
                    .padding(all = 28.px)
            ) {
                DashboardContent()
            }
        }
    }
}

// ── Top navigation bar ────────────────────────────────────────────────────────
@Composable
private fun TopNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.px)
            .backgroundColor(Theme.White.toColorMode())
            .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
            .padding(leftRight = 20.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo area — aligns with sidebar width
        Row(
            modifier = Modifier.width(240.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                src = if (ColorMode.current.isLight) Res.Image.logo else Res.Image.logoDark,
                description = "Story Tail Adventures",
                modifier = Modifier.height(30.px).margin(right = 10.px)
            )
            SpanText(
                text = "ADVENTURES ADMIN",
                modifier = Modifier
                    .color(Theme.TextGray.toColorMode())
                    .fontSize(10.px)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.SemiBold)
            )
        }

        // Search bar
        Box(modifier = Modifier.flexGrow(1).padding(leftRight = 40.px)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.px)
                    .backgroundColor(Theme.LightGray.toColorMode())
                    .borderRadius(8.px)
                    .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
                    .padding(leftRight = 14.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText(
                    text = "🔍",
                    modifier = Modifier.fontSize(13.px).margin(right = 8.px)
                )
                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .fillMaxWidth()
                        .height(36.px)
                        .backgroundColor(Colors.Transparent)
                        .color(Theme.TextGray.toColorMode())
                        .border(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                        .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(13.px)
                        .toAttrs { placeholder("Search destinations, deals...") }
                )
            }
        }

        // Actions — bell + dark mode toggle + CTA button
        Row(
            modifier = Modifier.gap(16.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var colorMode by ColorMode.currentState
            Box(
                modifier = Modifier
                    .width(36.px)
                    .height(36.px)
                    .backgroundColor(Theme.LightGray.toColorMode())
                    .borderRadius(8.px)
                    .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
                    .cursor(Cursor.Pointer),
                contentAlignment = Alignment.Center
            ) {
                SpanText("🔔", modifier = Modifier.fontSize(16.px))
            }
            // Dark/Light mode toggle
            Box(
                modifier = Modifier
                    .width(36.px)
                    .height(36.px)
                    .backgroundColor(Theme.LightGray.toColorMode())
                    .borderRadius(8.px)
                    .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
                    .color(Theme.DarkCharcoal.toColorMode())
                    .cursor(Cursor.Pointer)
                    .onClick { colorMode = colorMode.opposite },
                contentAlignment = Alignment.Center
            ) {
                if (colorMode.isLight) FaMoon(size = IconSize.SM)
                else FaSun(size = IconSize.SM)
            }
            Button(
                attrs = Modifier
                    .height(36.px)
                    .backgroundColor(Theme.Primary.toColorMode())
                    .color(Colors.White)
                    .borderRadius(8.px)
                    .border(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .padding(leftRight = 16.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(13.px)
                    .fontWeight(FontWeight.Medium)
                    .cursor(Cursor.Pointer)
                    .toAttrs()
            ) {
                SpanText("+ New Deal", modifier = Modifier.color(Colors.White).fontFamily(FONT_FAMILY))
            }
        }
    }
}

// ── Sidebar ───────────────────────────────────────────────────────────────────
@Composable
private fun AdminSidebar() {
    Column(
        modifier = Modifier
            .width(240.px)
            .minHeight(100.vh)
            .backgroundColor(Theme.White.toColorMode())
            .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
            .padding(top = 12.px, bottom = 24.px)
            .overflow(Overflow.Hidden)
    ) {
        sidebarSections.forEach { section ->
            SpanText(
                text = section.title,
                modifier = Modifier
                    .padding(leftRight = 20.px, topBottom = 6.px)
                    .margin(top = 12.px)
                    .color(Theme.TextGray.toColorMode())
                    .fontSize(10.px)
                    .fontWeight(FontWeight.SemiBold)
                    .fontFamily(FONT_FAMILY)
            )
            section.items.forEach { item -> NavMenuItem(item) }
        }
    }
}

@Composable
private fun NavMenuItem(item: NavItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = 10.px, topBottom = 2.px)
            .cursor(Cursor.Pointer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.px)
                .borderRadius(8.px)
                .backgroundColor(if (item.isActive) Theme.SecondaryLight.toColorMode() else Colors.Transparent)
                .padding(leftRight = 12.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SpanText(
                    text = item.icon,
                    modifier = Modifier
                        .fontSize(14.px)
                        .margin(right = 10.px)
                        .color(if (item.isActive) Theme.DarkCharcoal.toColorMode() else Theme.TextGray.toColorMode())
                )
                SpanText(
                    text = item.label,
                    modifier = Modifier
                        .color(if (item.isActive) Theme.DarkCharcoal.toColorMode() else Theme.TextGray.toColorMode())
                        .fontSize(14.px)
                        .fontFamily(FONT_FAMILY)
                        .fontWeight(if (item.isActive) FontWeight.SemiBold else FontWeight.Normal)
                )
            }
            if (item.badge != null) {
                Box(
                    modifier = Modifier
                        .backgroundColor(GreenAccent)
                        .borderRadius(4.px)
                        .padding(leftRight = 6.px, topBottom = 2.px),
                    contentAlignment = Alignment.Center
                ) {
                    SpanText(
                        text = item.badge,
                        modifier = Modifier
                            .color(Colors.White)
                            .fontSize(10.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Bold)
                    )
                }
            }
        }
    }
}

// ── Dashboard content ──────────────────────────────────────────────────────────
@Composable
private fun DashboardContent() {
    var activeFilter by remember { mutableStateOf("Today") }

    // Welcome header + time filter
    Row(
        modifier = Modifier.fillMaxWidth().margin(bottom = 24.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SpanText(
                text = "Welcome back, Alex! 👋",
                modifier = Modifier
                    .color(Theme.DarkCharcoal.toColorMode())
                    .fontSize(26.px)
                    .fontWeight(FontWeight.Bold)
                    .fontFamily(FONT_FAMILY)
            )
            SpanText(
                text = "Here's what's happening with your travel packages today.",
                modifier = Modifier
                    .color(Theme.TextGray.toColorMode())
                    .fontSize(14.px)
                    .fontFamily(FONT_FAMILY)
                    .margin(top = 4.px)
            )
        }
        // Today / Week / Month toggle
        Row(
            modifier = Modifier
                .backgroundColor(Theme.White.toColorMode())
                .borderRadius(8.px)
                .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
                .padding(all = 4.px)
        ) {
            listOf("Today", "Week", "Month").forEach { label ->
                Box(
                    modifier = Modifier
                        .backgroundColor(if (activeFilter == label) Theme.Primary.toColorMode() else Colors.Transparent)
                        .borderRadius(6.px)
                        .padding(leftRight = 16.px, topBottom = 6.px)
                        .cursor(Cursor.Pointer)
                        .onClick { activeFilter = label },
                    contentAlignment = Alignment.Center
                ) {
                    SpanText(
                        text = label,
                        modifier = Modifier
                            .color(if (activeFilter == label) Colors.White else Theme.TextGray.toColorMode())
                            .fontSize(13.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(if (activeFilter == label) FontWeight.Medium else FontWeight.Normal)
                    )
                }
            }
        }
    }

    // Stat cards row
    Row(
        modifier = Modifier.fillMaxWidth().gap(16.px).margin(bottom = 24.px)
    ) {
        statCards.forEach { card ->
            StatCard(modifier = Modifier.weight(1f), card = card)
        }
    }

    // Analytics + Quick Tasks
    Row(
        modifier = Modifier.fillMaxWidth().gap(16.px)
    ) {
        BookingAnalyticsPanel(modifier = Modifier.weight(2f))
        QuickTasksPanel(modifier = Modifier.weight(1f))
    }
}

// ── Stat card ──────────────────────────────────────────────────────────────────
@Composable
private fun StatCard(modifier: Modifier, card: StatCardData) {
    Column(
        modifier = modifier
            .backgroundColor(Theme.White.toColorMode())
            .borderRadius(12.px)
            .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
            .padding(all = 20.px)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().margin(bottom = 14.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                text = card.title,
                modifier = Modifier
                    .color(Theme.TextGray.toColorMode())
                    .fontSize(11.px)
                    .fontWeight(FontWeight.SemiBold)
                    .fontFamily(FONT_FAMILY)
            )
            Box(
                modifier = Modifier
                    .width(40.px)
                    .height(40.px)
                    .borderRadius(8.px)
                    .backgroundColor(card.iconBg),
                contentAlignment = Alignment.Center
            ) {
                SpanText(text = card.icon, modifier = Modifier.fontSize(18.px))
            }
        }
        SpanText(
            text = card.value,
            modifier = Modifier
                .color(Theme.DarkCharcoal.toColorMode())
                .fontSize(28.px)
                .fontWeight(FontWeight.Bold)
                .fontFamily(FONT_FAMILY)
                .margin(bottom = 8.px)
        )
        SpanText(
            text = card.trendText,
            modifier = Modifier
                .color(card.trendColor)
                .fontSize(12.px)
                .fontFamily(FONT_FAMILY)
        )
    }
}

// ── Booking analytics panel ────────────────────────────────────────────────────
@Composable
private fun BookingAnalyticsPanel(modifier: Modifier) {
    Column(
        modifier = modifier
            .backgroundColor(Theme.White.toColorMode())
            .borderRadius(12.px)
            .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
            .padding(all = 20.px)
    ) {
        // Panel header
        Row(
            modifier = Modifier.fillMaxWidth().margin(bottom = 20.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                text = "Booking Analytics",
                modifier = Modifier
                    .color(Theme.DarkCharcoal.toColorMode())
                    .fontSize(16.px)
                    .fontWeight(FontWeight.SemiBold)
                    .fontFamily(FONT_FAMILY)
            )
            SpanText(
                text = "•••",
                modifier = Modifier
                    .color(Theme.TextGray.toColorMode())
                    .fontSize(14.px)
                    .cursor(Cursor.Pointer)
            )
        }

        // Chart — y-axis labels alongside SVG
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .width(28.px)
                    .height(175.px)
                    .margin(right = 8.px),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("25", "20", "15").forEach { label ->
                    SpanText(
                        text = label,
                        modifier = Modifier
                            .color(Theme.TextGray.toColorMode())
                            .fontSize(11.px)
                            .fontFamily(FONT_FAMILY)
                    )
                }
            }
            Box(modifier = Modifier.flexGrow(1).height(175.px)) {
                // Horizontal grid lines
                Column(
                    modifier = Modifier.fillMaxWidth().height(175.px),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.px)
                                .backgroundColor(Theme.Border.toColorMode())
                        )
                    }
                }
                // Primary area chart (clip-path polygon approximates a rising curve)
                // Light: matches Theme.Primary.rgb (123,26,26), Dark: matches Theme.Primary.darkRgb (232,135,42)
                val primaryChartGradient = if (ColorMode.current.isLight) {
                    "linear-gradient(to bottom, rgba(123,26,26,0.28), rgba(123,26,26,0.04))"
                } else {
                    "linear-gradient(to bottom, rgba(232,135,42,0.28), rgba(232,135,42,0.04))"
                }
                val primaryChartStroke = if (ColorMode.current.isLight) {
                    "rgb(123,26,26)"
                } else {
                    "rgb(232,135,42)"
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.px)
                        .styleModifier {
                            property("background", primaryChartGradient)
                            property("clip-path", "polygon(0% 93%, 12% 87%, 25% 74%, 37% 58%, 50% 42%, 62% 28%, 75% 15%, 87% 8%, 100% 3%, 100% 100%, 0% 100%)")
                        }
                )
                // Primary line stroke (thin strip along top edge of area)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.px)
                        .styleModifier {
                            property("background", primaryChartStroke)
                            property("clip-path", "polygon(0% 93%, 12% 87%, 25% 74%, 37% 58%, 50% 42%, 62% 28%, 75% 15%, 87% 8%, 100% 3%, 100% 4.5%, 87% 9.5%, 75% 16.5%, 62% 29.5%, 50% 43.5%, 37% 59.5%, 25% 75.5%, 12% 88.5%, 0% 94.5%)")
                        }
                )
                // Green area chart
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.px)
                        .styleModifier {
                            property("background", "linear-gradient(to bottom, rgba(16,185,129,0.18), rgba(16,185,129,0.03))")
                            property("clip-path", "polygon(0% 99%, 12% 97%, 25% 93%, 37% 88%, 50% 82%, 62% 75%, 75% 67%, 87% 60%, 100% 57%, 100% 100%, 0% 100%)")
                        }
                )
                // Green line stroke
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.px)
                        .styleModifier {
                            property("background", "rgb(16,185,129)")
                            property("clip-path", "polygon(0% 99%, 12% 97%, 25% 93%, 37% 88%, 50% 82%, 62% 75%, 75% 67%, 87% 60%, 100% 57%, 100% 58.1%, 87% 61.1%, 75% 68.1%, 62% 76.1%, 50% 83.1%, 37% 89.1%, 25% 94.1%, 12% 98.1%, 0% 100.1%)")
                        }
                )
            }
        }
    }
}

// ── Quick tasks panel ──────────────────────────────────────────────────────────
@Composable
private fun QuickTasksPanel(modifier: Modifier) {
    var activeTab by remember { mutableStateOf("To Do") }

    Column(
        modifier = modifier
            .backgroundColor(Theme.White.toColorMode())
            .borderRadius(12.px)
            .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
            .padding(all = 20.px)
    ) {
        // Header + tabs
        Row(
            modifier = Modifier.fillMaxWidth().margin(bottom = 16.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                text = "Quick Tasks",
                modifier = Modifier
                    .color(Theme.DarkCharcoal.toColorMode())
                    .fontSize(16.px)
                    .fontWeight(FontWeight.SemiBold)
                    .fontFamily(FONT_FAMILY)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                listOf("To Do", "Events").forEach { tab ->
                    Column(
                        modifier = Modifier
                            .padding(leftRight = 8.px)
                            .cursor(Cursor.Pointer)
                            .onClick { activeTab = tab },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SpanText(
                            text = tab,
                            modifier = Modifier
                                .color(if (activeTab == tab) Theme.Primary.toColorMode() else Theme.TextGray.toColorMode())
                                .fontSize(13.px)
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(if (activeTab == tab) FontWeight.SemiBold else FontWeight.Normal)
                        )
                        if (activeTab == tab) {
                            Box(
                                modifier = Modifier
                                    .width(32.px)
                                    .height(2.px)
                                    .backgroundColor(Theme.Primary.toColorMode())
                                    .margin(top = 4.px)
                            )
                        }
                    }
                }
            }
        }

        // Task input + ADD button
        Row(
            modifier = Modifier.fillMaxWidth().margin(bottom = 16.px).gap(8.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Input(
                type = InputType.Text,
                attrs = Modifier
                    .weight(1f)
                    .height(40.px)
                    .backgroundColor(Theme.LightGray.toColorMode())
                    .color(Theme.DarkCharcoal.toColorMode())
                    .borderRadius(8.px)
                    .border(width = 1.px, style = LineStyle.Solid, color = Theme.Border.toColorMode())
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .padding(leftRight = 14.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(13.px)
                    .toAttrs { placeholder("What needs to be done?") }
            )
            Button(
                attrs = Modifier
                    .height(40.px)
                    .backgroundColor(Theme.Primary.toColorMode())
                    .color(Colors.White)
                    .borderRadius(8.px)
                    .border(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .padding(leftRight = 16.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(13.px)
                    .fontWeight(FontWeight.Medium)
                    .cursor(Cursor.Pointer)
                    .toAttrs()
            ) {
                SpanText("ADD", modifier = Modifier.color(Colors.White).fontFamily(FONT_FAMILY))
            }
        }

        // Task list
        Column(modifier = Modifier.fillMaxWidth().gap(4.px)) {
            sampleTasks.forEach { task -> TaskRow(task) }
        }
    }
}

@Composable
private fun TaskRow(task: TaskItemData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 10.px),
        verticalAlignment = Alignment.Top
    ) {
        // Checkbox indicator
        Box(
            modifier = Modifier
                .width(18.px)
                .height(18.px)
                .margin(top = 2.px, right = 12.px)
                .borderRadius(4.px)
                .border(
                    width = 2.px,
                    style = LineStyle.Solid,
                    color = if (task.done) RedAccent else Theme.TextDarkGray.toColorMode()
                )
                .backgroundColor(if (task.done) RedAccent else Colors.Transparent)
                .cursor(Cursor.Pointer),
            contentAlignment = Alignment.Center
        ) {
            if (task.done) {
                SpanText("✓", modifier = Modifier.color(Colors.White).fontSize(11.px))
            }
        }
        // Task text + badge
        Column(modifier = Modifier.flexGrow(1)) {
            SpanText(
                text = task.text,
                modifier = Modifier
                    .color(if (task.done) Theme.TextGray.toColorMode() else Theme.DarkCharcoal.toColorMode())
                    .fontSize(13.px)
                    .fontFamily(FONT_FAMILY)
                    .let { if (task.done) it.textDecorationLine(TextDecorationLine.LineThrough) else it }
            )
            if (task.badge != null && task.badgeColor != null) {
                Box(
                    modifier = Modifier
                        .margin(top = 4.px)
                        .backgroundColor(task.badgeColor)
                        .borderRadius(4.px)
                        .padding(leftRight = 6.px, topBottom = 2.px),
                    contentAlignment = Alignment.Center
                ) {
                    SpanText(
                        text = task.badge,
                        modifier = Modifier
                            .color(Colors.White)
                            .fontSize(10.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Medium)
                    )
                }
            }
        }
    }
}
