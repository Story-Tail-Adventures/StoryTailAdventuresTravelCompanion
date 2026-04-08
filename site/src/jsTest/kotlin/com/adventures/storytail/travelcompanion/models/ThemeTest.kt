package com.adventures.storytail.travelcompanion.models

import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.rgb
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ThemeTest {

    @Test
    fun allEntriesHaveLightAndDarkHexValues() {
        Theme.entries.forEach { theme ->
            assertTrue(theme.hex.startsWith("#"), "${theme.name} light hex should start with #")
            assertTrue(theme.darkHex.startsWith("#"), "${theme.name} dark hex should start with #")
            assertTrue(theme.hex.length == 7, "${theme.name} light hex should be 7 chars (#RRGGBB)")
            assertTrue(theme.darkHex.length == 7, "${theme.name} dark hex should be 7 chars (#RRGGBB)")
        }
    }

    @Test
    fun lightAndDarkColorsAreDifferent() {
        Theme.entries.forEach { theme ->
            assertNotEquals(
                theme.hex, theme.darkHex,
                "${theme.name} should have different light and dark hex values"
            )
        }
    }

    @Test
    fun forColorModeLightReturnsLightRgb() {
        Theme.entries.forEach { theme ->
            assertEquals(
                theme.rgb, theme.forColorMode(ColorMode.LIGHT),
                "${theme.name}.forColorMode(LIGHT) should return rgb"
            )
        }
    }

    @Test
    fun forColorModeDarkReturnsDarkRgb() {
        Theme.entries.forEach { theme ->
            assertEquals(
                theme.darkRgb, theme.forColorMode(ColorMode.DARK),
                "${theme.name}.forColorMode(DARK) should return darkRgb"
            )
        }
    }

    @Test
    fun primaryLightIsMaroon() {
        assertEquals("#7B1A1A", Theme.Primary.hex)
    }

    @Test
    fun primaryDarkIsOrange() {
        assertEquals("#E8872A", Theme.Primary.darkHex)
    }

    @Test
    fun secondaryLightIsOrange() {
        assertEquals("#F6931D", Theme.Secondary.hex)
    }

    @Test
    fun secondaryDarkIsBlue() {
        assertEquals("#3B8ED6", Theme.Secondary.darkHex)
    }

    @Test
    fun darkCharcoalInvertsForDarkMode() {
        // Light: near-black text, Dark: off-white text
        assertEquals("#2D2926", Theme.DarkCharcoal.hex)
        assertEquals("#F0EDE8", Theme.DarkCharcoal.darkHex)
    }

    @Test
    fun whiteInvertsForDarkMode() {
        // Light: white surfaces, Dark: near-black surfaces
        assertEquals("#FFFFFF", Theme.White.hex)
        assertEquals("#1A1A1A", Theme.White.darkHex)
    }

    @Test
    fun backgroundHasDarkVariant() {
        assertEquals("#FFFAF5", Theme.Background.hex)
        assertEquals("#121212", Theme.Background.darkHex)
    }

    @Test
    fun borderHasSubtleValues() {
        assertEquals("#E6DCD2", Theme.Border.hex)
        assertEquals("#2E3338", Theme.Border.darkHex)
    }

    @Test
    fun themeEnumHasExpectedEntryCount() {
        assertEquals(11, Theme.entries.size)
    }

    @Test
    fun allExpectedEntriesExist() {
        val expectedNames = setOf(
            "Primary", "PrimaryDark", "Secondary", "SecondaryLight",
            "DarkCharcoal", "White", "Background", "TextGray",
            "TextDarkGray", "LightGray", "Border"
        )
        val actualNames = Theme.entries.map { it.name }.toSet()
        assertEquals(expectedNames, actualNames)
    }
}
