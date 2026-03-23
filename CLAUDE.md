# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Story Tail Adventures Travel Companion** is a Kotlin Multiplatform (KMP) project targeting Android, iOS, and Web (via Kobweb). It is in early development — the landing page, admin dashboard UI, and Supabase auth backend are in place, but frontend-backend integration and travel companion features are not yet implemented.

## Build & Development Commands

### Gradle (Android + Shared + iOS)

```bash
./gradlew assemble          # Build all modules without testing
./gradlew build             # Build and run all tests
./gradlew test              # Run unit tests only
./gradlew test --max-workers 1 --scan   # CI-style test run
./gradlew clean             # Delete build directories
```

### Web (Kobweb — run from `site/` directory)

```bash
cd site
kobweb run                  # Start dev server at http://localhost:8080 (press Q to stop)
kobweb export               # Build for production
kobweb run --env prod --notty  # Headless production mode
```

### Docker (Web)

The `Dockerfile` does a multi-stage build using Java 17 + Kobweb CLI 0.19.2. Requires `SUPABASE_URL` and `SUPABASE_KEY` env vars injected at runtime.

## Environment Variables

Required for the site backend (Supabase auth):

| Variable | Purpose |
|---|---|
| `SUPABASE_URL` | Supabase project URL |
| `SUPABASE_KEY` | Supabase anon/service key |

See `.env.example` at the repo root. Missing vars produce warnings but don't crash the server.

## Module Architecture

```
Root
├── shared/         Kotlin Multiplatform library — shared business logic
├── androidApp/     Android application (depends on shared)
├── iosApp/         iOS application (depends on shared via static framework)
└── site/           Kobweb full-stack web app (JS frontend + JVM backend)
```

### shared

- **commonMain** — platform-agnostic code (`Greeting`, `Platform` interface)
- **androidMain / iosMain** — `actual` implementations of `expect` declarations
- Expect/actual pattern is used for platform-specific behavior

### androidApp

- Entry point: `androidApp/src/main/java/com/adventures/storytail/travelcompanion/android/MainActivity.kt`
- Material3 theme with dark/light support
- Min SDK: 27 · Target/Compile SDK: 34 · JVM target: 1.8
- Namespace: `com.adventures.storytail.travelcompanion.android`

### site (Kobweb)

The site module is split across three source sets:

- **jsMain** (frontend): Pages, components, styles, theme
- **jvmMain** (backend): API routes, Supabase auth client, data models
- **commonMain**: Shared `expect` declarations (User, UserWithoutPassword)

**Frontend routes:**
- `/` — landing page (header, hero, destinations, footer)
- `/admin/login` — login form (UI complete, sign-in button onClick not yet wired)
- `/admin/home` — admin dashboard (UI complete, no backend integration)

**Backend auth flow:**
- `SupabaseDb.kt` — Ktor HTTP client calling Supabase's `/auth/v1/token?grant_type=password`
- `UserCheck.kt` — API route at `/usercheck` accepting `LoginRequest(email, password)`, returning `AuthResponse` (JWT tokens)
- `@InitApi` function `initSupabase()` registers `SupabaseDb` into Kobweb's `context.data`
- API routes access it via `context.data.getValue<SupabaseDb>()`

**Design system** (defined in `Theme.kt` and `Constants.kt`):
- Colors: Primary (orange), DarkCharcoal, AccentBlue, LightBlue, White, TextGray variants
- Fonts: Raleway (headings), Mulish (body), Roboto (default) — loaded via Google Fonts CDN in `AppEntry.kt`

## Key Dependencies

Managed via the version catalog at `gradle/libs.versions.toml`.

| Dependency | Version |
|---|---|
| Kotlin | 2.0.21 |
| Android Gradle Plugin | 8.7.0 |
| Jetpack Compose | 1.7.3 |
| JetBrains Compose (KMP) | 1.7.0 |
| Kobweb | 0.19.2 |
| Ktor (HTTP client) | 2.3.12 |
| Gradle | 8.9 |

## CI/CD

GitHub Actions workflow at `.github/workflows/build.yml`:
- **Build job:** `./gradlew assemble --scan` on push/PR to `main`
- **Test job:** `./gradlew test --max-workers 1 --scan`, uploads results to Codecov
- Concurrency configured to cancel in-progress runs for the same ref

Dependabot enabled for weekly Gradle dependency updates.

## Gradle Configuration

- JVM memory: 2048M (Gradle daemon + Kotlin daemon)
- Build cache and configuration cache both **enabled**
- Java toolchain: temurin-17 (see `.java-version`)
- Kotlin JVM target for `shared`: 17; for `androidApp`: 1.8
- Compiler flag `-Xexpect-actual-classes` enabled
- No linting tools (ktlint, detekt) are configured