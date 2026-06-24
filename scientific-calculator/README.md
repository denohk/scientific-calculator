# Split-Flap Scientific Calculator

A beautifully engineered, **first-of-its-kind** Scientific Calculator for Android built entirely with **Jetpack Compose**. 

Unlike standard flat interfaces, we have completely renewed the calculator experience with a **True 3D Split-Flap Physics Engine**—a feature that is **completely unique in the app industry**. Every number and operator you type—and every result in the scrolling history tape—mechanically flips into place, mimicking the authentic physics of retro train-station displays and mechanical alarm clocks.

## Features

- **True 3D Physics Engine**: Instead of cheap 2D zooming, the characters are mathematically sliced into a Top Half and Bottom Half. The top half unhinges and physically falls forward (`rotationX`) in 3D space to reveal the next character.
- **Logarithmic Spool-Up**: The flipping animation uses an exponential speed curve (`remaining^4`). It starts incredibly slow (so you can watch the plate physically pivot) and then accelerates to a blur as the mechanical motor catches up.
- **Context-Aware Reels**: Numbers only cycle through numbers (`0-9`); operators only cycle through math symbols.
- **Scalable Vector Aesthetics**: The metallic brushed-blue casing is built entirely with infinite-resolution Compose vectors. No static image files, no blurriness.
- **Full Scientific Math**: Supports standard arithmetic, trigonometry, logarithms, powers, and complex parentheses evaluation.

## 🛠 How to Fork & Upgrade

Are you a programmer who loves physics-based UIs or Jetpack Compose? I invite you to **Fork** this repository and build upon it! 

Here are some cool ways you can upgrade the project:
1. **Sound Effects**: Add authentic mechanical "clacking" sounds that sync precisely with the `delay()` calls in the flip engine.
2. **Themes**: Add a toggle for different plate materials (e.g., Matte Black, Vintage Yellow, Glowing Cyberpunk).
3. **Advanced Math**: Expand the split-flap engine to support Calculus symbols, matrices, or graphing tapes.

### To get started:
1. Click the **Fork** button at the top right of this repository.
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/scientific-calculator.git
   ```
3. Open the project in **Android Studio** (or your preferred IDE).
4. Build and run it on an Android Emulator or a physical device.
5. Dive into `MainScreen.kt` to see the `AnimatedFlipPlate` mechanics in action!
6. Submit a **Pull Request** back here when you've built something awesome.

Happy coding!
