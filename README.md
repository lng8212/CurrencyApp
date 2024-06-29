

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

*Android

![Screen_recording_20240629_184619](https://github.com/lng8212/CurrencyApp/assets/69128515/820cadf1-d036-457c-9758-93ad5cca1d11)


*iOS 
![Simulator Screenshot - iPhone 15 - 2024-06-29 at 19 00 03](https://github.com/lng8212/CurrencyApp/assets/69128515/a5836ae6-a480-4771-a68c-21e26a1ce0f9)

