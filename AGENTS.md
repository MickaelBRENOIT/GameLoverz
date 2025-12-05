# Repository Guidelines

## Project Structure & Modules
- `app/` is the single Android application module; version catalog lives in `gradle/libs.versions.toml`.
- `app/src/main/java/com/mickaelbrenoit/gameloverz` holds Kotlin + Jetpack Compose UI (entry point `MainActivity.kt`, theme in `ui/theme/`).
- `app/src/main/res` contains XML resources (layouts, drawables, values); name resources in `lowercase_snake_case`.
- Tests: host-side unit tests in `app/src/test`, instrumented/UI tests in `app/src/androidTest`. Gradle wrapper and build config sit at the repo root.

## Build, Test, and Development Commands
- `./gradlew assembleDebug` builds a debuggable APK.
- `./gradlew lint` runs Android lint/static analysis; fix or suppress findings before merging.
- `./gradlew test` runs local JVM unit tests under `app/src/test`.
- `./gradlew connectedAndroidTest` runs device/emulator instrumentation and Compose UI tests; requires a booted device.
- `./gradlew clean` (optional) to reset the build cache if you hit toolchain hiccups.

## Coding Style & Naming Conventions
- Kotlin style: 4-space indentation, `PascalCase` for classes/composables, `lowerCamelCase` for functions/vars, constants in `UPPER_SNAKE_CASE`.
- Compose: keep composables small and stateless when possible; hoist state to callers. Preview functions should be annotated with `@Preview` and named `*Preview`.
- Resources: keep strings in `res/values/strings.xml`; avoid hardcoded text in composables.
- Use Android Studio’s formatter; ensure code passes `lint` before opening a PR.

## Testing Guidelines
- Prefer fast JVM tests for logic; use AndroidX Compose testing APIs for UI behavior.
- Name tests after the subject under test (e.g., `GreetingTest`, `GameLoverzAppTest`) and methods after behavior: `fun clickingFavorites_navigatesToFavorites()`.
- Run `./gradlew test` for unit coverage and `./gradlew connectedAndroidTest` for device-dependent flows prior to review.

## Commit & Pull Request Guidelines
- Commits: keep messages short and imperative (`Add navigation scaffold`, `Fix favorites selection`). Include a brief body describing intent and any follow-up risks.
- Pull Requests: include a clear summary of changes, testing performed (commands, device/emulator details), and link related issues. Attach screenshots or screen recordings for UI-visible changes. Note any lint/test gaps or TODOs explicitly.

## Environment & Configuration Notes
- Target/compile SDK 36, min SDK 31; Kotlin 2.0.x and AGP 8.13.x via the version catalog.
- Ensure `local.properties` points to a valid Android SDK. When updating dependencies, prefer editing `gradle/libs.versions.toml` and syncing via the wrapper.
