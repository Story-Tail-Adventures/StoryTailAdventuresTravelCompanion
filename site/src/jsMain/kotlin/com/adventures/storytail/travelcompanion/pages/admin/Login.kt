package com.adventures.storytail.travelcompanion.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.adventures.storytail.travelcompanion.models.AuthResponse
import com.adventures.storytail.travelcompanion.models.LoginRequest
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.models.toColorMode
import com.adventures.storytail.travelcompanion.styles.LoginInputStyle
import com.adventures.storytail.travelcompanion.util.Constants.AUTH_TOKEN_KEY
import com.adventures.storytail.travelcompanion.util.Constants.FONT_FAMILY
import com.adventures.storytail.travelcompanion.util.Id
import com.adventures.storytail.travelcompanion.util.Res
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement

@Page
@Composable
fun LoginScreen() {

    var errorText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val ctx = rememberPageContext()
    var colorMode by ColorMode.currentState
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Dark/Light mode toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.px),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .color(Theme.DarkCharcoal.toColorMode())
                    .cursor(Cursor.Pointer)
                    .onClick { colorMode = colorMode.opposite }
            ) {
                if (colorMode.isLight) FaMoon(size = IconSize.LG)
                else FaSun(size = IconSize.LG)
            }
        }
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(Theme.LightGray.toColorMode()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = 50.px)
                    .width(350.px),
                src = if (ColorMode.current.isLight) Res.Image.logo else Res.Image.logoDark,
                description = "Logo"
            )
            Input(
                type = InputType.Email,
                attrs = LoginInputStyle.toModifier()
                    .id(Id.usernameInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Theme.White.toColorMode())
                    .toAttrs{
                        placeholder("Email")
                    }
            )
            Input(
                type = InputType.Password,
                attrs = LoginInputStyle.toModifier()
                    .id(Id.passwordInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Theme.White.toColorMode())
                    .toAttrs{
                        placeholder("Password")
                    }
            )
            Button(
                attrs = Modifier
                    .width(350.px)
                    .height(54.px)
                    .backgroundColor(Theme.Primary.toColorMode())
                    .color(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .fontWeight(FontWeight.Medium)
                    .border(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .cursor(Cursor.Pointer)
                    .onClick {
                        scope.launch {
                            val email = (document.getElementById(Id.usernameInput) as? HTMLInputElement)?.value.orEmpty()
                            val password = (document.getElementById(Id.passwordInput) as? HTMLInputElement)?.value.orEmpty()

                            if (email.isBlank() || password.isBlank()) {
                                errorText = "Please enter both email and password"
                                return@launch
                            }

                            val requestBody = Json.encodeToString(LoginRequest(email = email, password = password))

                            try {
                                val responseBytes = window.api.tryPost(
                                    apiPath = "usercheck",
                                    body = requestBody.encodeToByteArray()
                                )

                                if (responseBytes == null) {
                                    errorText = "Network error. Please try again."
                                    return@launch
                                }

                                val responseText = responseBytes.decodeToString()

                                val authResponse = try {
                                    Json.decodeFromString<AuthResponse>(responseText)
                                } catch (_: Exception) {
                                    null
                                }

                                if (authResponse != null && authResponse.accessToken.isNotBlank()) {
                                    window.localStorage.setItem(AUTH_TOKEN_KEY, authResponse.accessToken)
                                    ctx.router.navigateTo("/admin/home")
                                    return@launch
                                }

                                val errorMap = try {
                                    Json.decodeFromString<Map<String, String>>(responseText)
                                } catch (_: Exception) {
                                    null
                                }
                                errorText = errorMap?.get("error") ?: "Login failed"
                            } catch (e: Exception) {
                                errorText = "An unexpected error occurred. Please try again."
                            }
                        }
                    }
                    .toAttrs()
            ){
                SpanText(text = "Sign in")
            }
            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center),
                text = errorText
            )
        }
        }
    }
}