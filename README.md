# 🏆 Fetch Rewards Multiplatform App

Welcome to the **Fetch Rewards Multiplatform App**, a Kotlin Multiplatform (KMP) project designed to deliver a seamless experience on both **Android** and **iOS** platforms. This application fetches and displays user data in a clean, grouped table format, utilizing modern tools and best practices.

---

## 🚀 Features

- **Kotlin Multiplatform (KMP):** Shared business logic and UI across Android and iOS using Compose Multiplatform.
- **Dependency Injection (DI):** Modularized and testable architecture powered by [Koin](https://insert-koin.io/).
- **Networking:** Lightweight and efficient API handling with [Ktor](https://ktor.io/).
- **Navigation:** Simple and declarative navigation using [Voyager](https://github.com/adrielcafe/voyager).
- **Architecture:** Built with the **MVVM (Model-View-ViewModel)** pattern for scalability and clean code.
- **UI Frameworks:**
  - **Compose Multiplatform** for shared, declarative UI.
  - **SwiftUI** for iOS-specific entry points.

---

## 🗂 Folder Structure

```plaintext
.
├── /composeApp                # Shared Compose Multiplatform code
│   ├── /commonMain            # Shared code for all platforms
│   ├── /androidMain           # Platform-specific code for Android
│   ├── /iosMain               # Platform-specific code for iOS
│
├── /iosApp                    # iOS application entry point
│   ├── AppDelegate.swift      # iOS application setup
│   ├── ContentView.swift      # SwiftUI entry point (optional)
│
└── /androidApp                # Android application entry point-multiplatform-dev/get-started.html)
```

# 💡 Tech Stack

## 🛠 Tools & Frameworks

- **Kotlin Multiplatform (KMP):** Shared logic for Android and iOS.
- **Compose Multiplatform:** Shared, declarative UI for Android and iOS.
- **Koin:** Dependency Injection for modular and testable architecture.
- **Ktor:** Networking library for API calls.
- **Voyager:** Lightweight and intuitive navigation framework.
- **SwiftUI:** iOS-specific UI, where applicable.

---

## 🏛 Architecture

- **MVVM (Model-View-ViewModel):** Ensures clean separation of concerns for better maintainability and scalability.

---

## ✨ How It Works

1. The app fetches user data from the provided API endpoint using **Ktor**.
2. Data is processed to:
   - Filter out entries where `name` is blank or `null`.
   - Group entries by `listId`.
   - Sort entries first by `listId` and then alphabetically by `name`.
3. The processed data is displayed in a **table format** with group headers and interactive rows.

---

## 📱 Screenshots

### Android

| **List View**               |     
|-----------------------------|
| ![Android List View](![Screenshot_20250105_034505](https://github.com/user-attachments/assets/1845ec92-f581-47d8-8602-dfd7d1c45cb1)
)


---

## 📝 How to Run

### Prerequisites

- **Kotlin 1.9.0** or later.
- **Android Studio Flamingo** or later.
- **Xcode 14.0** or later for iOS development.
