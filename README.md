# Scientific Calculator (Android)

A modern, robust, and aesthetically pleasing Scientific Calculator built natively for Android using **Jetpack Compose**. This app goes beyond basic calculations by offering a fluid user experience with dynamic UI adaptations and advanced mathematical operations.

## ✨ Key Features

* **Scrollable History Tape:** Keep track of your past calculations. The display acts like a real calculator tape, auto-scrolling upward as you compute, while keeping your current input pinned at the bottom.
* **Dynamic Text Resizing:** Never worry about your numbers getting cut off. The display automatically shrinks the font size of your expressions and results as they get longer, ensuring everything always fits perfectly on the screen.
* **Robust Layout:** Features a fixed-height, bulletproof button grid. No matter how large the calculation display gets, the keyboard remains stable and accessible without shifting or shrinking.
* **Continuous Chaining:** Seamlessly chain calculations. If you finish a calculation and immediately press an operator (like `+` or `/`), the app automatically carries over your previous result to start the new expression.
* **Scientific Operations:** Includes standard operations (`+`, `-`, `*`, `/`) alongside scientific functions:
  * Trigonometry: `sin`, `cos`, `tan`
  * Logarithms: `log` (base 10), `ln` (natural log)
  * Advanced: `sqrt`, `^` (power), `1/x` (inverse)
  * Parentheses for complex grouping `()`

## 🛠️ Tech Stack

* **UI Toolkit:** Jetpack Compose
* **Language:** Kotlin
* **Math Parsing:** `exp4j` expression builder for safe and accurate string evaluation
* **Architecture:** 100% Declarative UI with state hoisting

## 🚀 Getting Started

To run this project locally:

1. Clone the repository.
2. Open the project in Android Studio (or VS Code with Android extensions).
3. Build and run on an Android Emulator or a physical Android device.

## 📱 Screenshots
<img width="386" height="888" alt="image" src="https://github.com/user-attachments/assets/136bcd97-bd56-45f0-b650-33016fc23c52" />

*(You can add screenshots of your calculator here later to show off the beautiful UI!)*

## 📄 License

This project is open-source and available for educational and personal use.
