# Animated 3D Scientific Calculator (Android)

A state-of-the-art, visually stunning Scientific Calculator built natively for Android using **Jetpack Compose**. This app goes far beyond basic calculation by offering a highly tactile user experience, complete with mechanical flip-board animations, responsive 3D keys, and dynamic layout adaptations.

## ✨ Key Features

* **Mechanical Split-Flap Display:** Watch your numbers flip into place like a classic train station departure board! The calculator uses complex 3D rotation animations (`graphicsLayer` + `rotationX`) to physically "flip" digits as you type.
* **Animation Speed Controller:** Don't like the flip speed? Use the sleek top-left slider to adjust the mechanical flip animation speed from `Stop` (instant) all the way to `Fast`.
* **Tactile 3D Buttons:** The keypad feels alive. Every button is rendered as a 3D pill with gradient shading, casting a shadow base. Pressing a button triggers a magnetic scale animation and a physical Y-axis push downward, creating a highly satisfying, clicky feel.
* **Fully Responsive UI (Portrait & Landscape):** The calculator completely re-architects itself based on your device orientation:
  * **Portrait Mode:** Classic top-to-bottom layout with a perfectly weighted distribution so it fits any phone perfectly without overflowing.
  * **Landscape Mode:** Smoothly transitions into a split-screen layout! Your calculation tape sits on the left, while your 3D keypad automatically stretches into adaptable pill shapes on the right.
* **Scrollable History Tape:** No need to hunt for previous calculations. The entire display acts as an infinite scrolling tape. Keep tracking your past computations by simply scrolling up, while your current input is securely pinned to the bottom.
* **Continuous Chaining:** Finish a calculation and immediately press an operator (like `+` or `/`) to carry over your previous result and start a new sequence seamlessly.
* **Scientific Operations:** Includes standard operations alongside essential scientific functions: `sin`, `cos`, `tan`, `log`, `ln`, `sqrt`, `^`, `1/x`, and parentheses `()`.

## 🛠️ Tech Stack

* **UI Toolkit:** Jetpack Compose (100% Declarative UI)
* **Language:** Kotlin
* **Animations:** Custom Compose `Animatable`, `graphicsLayer` 3D rotations, and tween easings.
* **Math Parsing:** `exp4j` expression builder for safe, robust mathematical string evaluation.

## 🚀 Getting Started

To run this project locally:

1. Clone the repository.
2. Open the project in Android Studio.
3. Click the green **Run 'app'** button (▶️) to build and deploy to your Android Emulator or physical Android device. *Note: If you rotate the emulator, the UI will automatically adapt without needing a rebuild!*

## 📄 License

**Copyright © 2026 [Highperloop.com](https://highperloop.com). All Rights Reserved.**

This software and its source code are proprietary. You are welcome to view the code and use the application for your personal, non-commercial use. However, you may not copy, modify, distribute, or use the source code for commercial purposes without explicit written permission from the owner. This is **not** an open-source project.
