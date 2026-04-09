package com.adventures.storytail.travelcompanion.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthConstantsTest {

    @Test
    fun authTokenKeyIsNonBlank() {
        assertTrue(Constants.AUTH_TOKEN_KEY.isNotBlank())
    }

    @Test
    fun authTokenKeyValueIsAuthToken() {
        assertEquals("auth_token", Constants.AUTH_TOKEN_KEY)
    }

    @Test
    fun inputIdsAreDefined() {
        assertTrue(Id.usernameInput.isNotBlank(), "usernameInput ID should be non-blank")
        assertTrue(Id.passwordInput.isNotBlank(), "passwordInput ID should be non-blank")
    }
}
