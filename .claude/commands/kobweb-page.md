Wher# Kobweb Page Builder

Build a new Kobweb page or component for the Story Tail Adventures Travel Companion site module.

## Instructions

You are creating a Kobweb page/component for a Kotlin Multiplatform project. Follow these conventions exactly.

**User request:** $ARGUMENTS

## Before You Start

1. Read the Kobweb docs if needed: https://kobweb.varabyte.com/docs/getting-started/what-is-kobweb
2. Read the existing theme and constants to stay consistent:
   - `site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/models/Theme.kt` (color palette)
   - `site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/util/Constants.kt` (fonts, resource paths, IDs)
3. Read existing pages/components for reference patterns:
   - `site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/pages/Index.kt`
   - `site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/components/layouts/PageLayout.kt`
   - `site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/styles/HomeStyles.kt`

## Project Conventions

### Package & File Structure

```
site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/
├── pages/          # @Page composables (file path = route)
├── components/
│   ├── layouts/    # Layout wrappers (e.g., PageLayout)
│   └── sections/   # Reusable section composables
├── styles/         # CssStyle definitions
├── models/         # Theme enum, data models
└── util/           # Constants, helpers
```

- Package: `com.adventures.storytail.travelcompanion`
- Pages auto-route by file location: `pages/foo/Bar.kt` → `/foo/bar`

### Page Template

```kotlin
package com.adventures.storytail.travelcompanion.pages.{subpackage}

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.adventures.storytail.travelcompanion.components.layouts.PageLayout
import com.adventures.storytail.travelcompanion.models.Theme
import com.adventures.storytail.travelcompanion.util.Constants
import org.jetbrains.compose.web.css.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText

@Page
@Composable
fun PageName() {
    PageLayout {
        // Page content here
    }
}
```

### Theme Colors (use `Theme.X.rgb`)

| Name | Hex | Usage |
|------|-----|-------|
| Primary | #F6931D | Orange — CTAs, brand accent |
| DarkCharcoal | #242625 | Dark backgrounds |
| AccentBlue | #00AFE9 | Links, interactive elements |
| LightBlue | #E5F8FC | Light accent backgrounds |
| White | #FFFFFF | Text on dark, backgrounds |
| TextGray | #75939B | Muted body text |
| TextDarkGray | #82868B | Secondary text |
| LightGray | #FAFAFA | Card/form backgrounds |

### Typography

| Constant | Font | Usage |
|----------|------|-------|
| `Constants.FONT_FAMILY_HEADING` | Raleway | All headings (h1-h6, section titles) |
| `Constants.FONT_FAMILY_BODY` | Mulish | Body text, descriptions |
| `Constants.FONT_FAMILY` | Roboto | Default/fallback |

### Style Definitions (CssStyle)

Define reusable styles in `styles/` directory:

```kotlin
val MyComponentStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Theme.White.rgb)
            .transition(Transition.of("color", 300.ms))
    }
    hover {
        Modifier.color(Theme.AccentBlue.rgb)
    }
}
```

Apply with: `MyComponentStyle.toModifier().otherModifier(...)`

### Common Composable Patterns

**Layout:**
- `Box(modifier, contentAlignment)` — positioned container
- `Column(modifier, verticalArrangement, horizontalAlignment)` — vertical stack
- `Row(modifier, horizontalArrangement, verticalAlignment)` — horizontal stack

**Text:**
- `SpanText(text = "...", modifier = Modifier.fontFamily(Constants.FONT_FAMILY_HEADING).fontSize(2.rem))`

**Images:**
- `Image(src = "/path.webp", description = "alt text", modifier = ...)`

**Links:**
- `Link(path = "/route", modifier = ...) { SpanText("Link Text") }`

**Input fields:**
```kotlin
Input(
    type = InputType.Text,
    attrs = Modifier.width(350.px).height(54.px).toAttrs {
        placeholder("Enter value")
    }
)
```

**Buttons:**
```kotlin
Button(attrs = Modifier
    .backgroundColor(Theme.Primary.rgb)
    .color(Colors.White)
    .onClick { /* handler */ }
    .toAttrs()
) {
    SpanText("Button Text")
}
```

**State:**
```kotlin
var myState by remember { mutableStateOf(initialValue) }
```

**Icons (FontAwesome via Silk):**
```kotlin
import com.varabyte.kobweb.silk.components.icons.fa.*
FaBars(size = IconSize.LG)
FaFacebook()
```

### Backend API Route Template (if needed)

Place in `site/src/jvmMain/kotlin/com/adventures/storytail/travelcompanion/api/`:

```kotlin
package com.adventures.storytail.travelcompanion.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Api(routeOverride = "myroute")
suspend fun myRoute(context: ApiContext) {
    try {
        // Parse request
        val body = context.req.body?.decodeToString()
        // Process...
        context.res.setBodyText(Json.encodeToString(result))
    } catch (ex: Exception) {
        context.logger.error(ex.message.toString())
        context.res.setBodyText(Json.encodeToString(mapOf("error" to "Internal server error")))
    }
}
```

### Shared Models (if frontend + backend need the same type)

- `commonMain`: `expect class` / `expect data class`
- `jsMain`: `actual class` with `@Serializable`
- `jvmMain`: `actual class` with `@Serializable`

## Checklist

- [ ] Use `@Page` annotation for route pages
- [ ] Wrap page content in `PageLayout { }` for header/footer consistency
- [ ] Use `Theme.X.rgb` for all colors (never hardcode hex in composables)
- [ ] Use `Constants.FONT_FAMILY_HEADING` for headings, `FONT_FAMILY_BODY` for body
- [ ] Define reusable `CssStyle` blocks in `styles/` directory
- [ ] Use `.toModifier()` to convert CssStyle to Modifier
- [ ] Use `remember { mutableStateOf(...) }` for component state
- [ ] Place API routes in `jvmMain/api/` with `@Api` annotation
- [ ] Keep composables small — extract sections into separate `@Composable` functions
- [ ] Use responsive modifiers where appropriate
