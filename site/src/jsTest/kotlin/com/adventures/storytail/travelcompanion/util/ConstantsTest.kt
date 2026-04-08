package com.adventures.storytail.travelcompanion.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConstantsTest {

    @Test
    fun fontFamiliesAreDefined() {
        assertEquals("Roboto", Constants.FONT_FAMILY)
        assertEquals("Raleway", Constants.FONT_FAMILY_HEADING)
        assertEquals("Mulish", Constants.FONT_FAMILY_BODY)
    }

    @Test
    fun logoPathsAreDefined() {
        assertEquals("/stalogo.webp", Res.Image.logo)
        assertEquals("/stalogo-dark.png", Res.Image.logoDark)
    }

    @Test
    fun logoPathsStartWithSlash() {
        assertTrue(Res.Image.logo.startsWith("/"))
        assertTrue(Res.Image.logoDark.startsWith("/"))
    }

    @Test
    fun inputIdsAreDefined() {
        assertEquals("usernameInput", Id.usernameInput)
        assertEquals("passwordInput", Id.passwordInput)
    }
}
