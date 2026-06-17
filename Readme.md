<div align="center">

<img src="./ScreenShots/BMI Logo.png" width="300" align="center" alt="BMI Buddy Logo"/>

## **Calculate · Track · Share**

*A native Android app that calculates your Body Mass Index instantly — offline, ad-free, and beautifully simple.*

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Min API](https://img.shields.io/badge/Min%20API-21%20(Lollipop)-blue?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-success?style=flat-square)

</div>



## 💡 Why I Built This

Most BMI apps are either bloated with ads, demand an account to save your data, or bury the result behind five taps. All I wanted was: **enter height and weight → get BMI → understand what it means**. That's it.

So I built BMI Buddy - a clean, offline calculator that gives you your BMI, colour-coded category, and a shareable result in seconds. No sign-in. No cloud. No nonsense. Just the number you need, right when you need it.

This is also an exercise in doing more with less - single-activity architecture, zero third-party libraries, and a focus on making Android's native APIs do the heavy lifting. It's taught me a lot about Material Design, SharedPreferences, and what it actually takes to polish a small app into something people actually enjoy using.



## 🔍 What It Does

**Enter** your height and weight in metric or imperial. **Calculate** your BMI using the WHO-standard formula. **See** your result with a colour-coded category and animated progress bar. **Share** it anywhere - WhatsApp, Gmail, SMS, you name it.

That's the whole flow. No sign-in. Fully offline.



## ✨ Features

### Input & Units
- Toggle between **Metric** (cm + kg) and **Imperial** (ft/in + lbs) with a single tap
- Smart input validation - catches empty fields, negative numbers, and unrealistic values in real time
- Error messages shown inline on EditText fields, not as annoying toasts

### BMI Calculation
- Formula: `BMI = weight(kg) / height²(m²)` - WHO standard
- Handles unit conversion internally; no manual math required

### Results Display
- **Colour-coded categories** aligned to WHO standards:
  - 🔵 Underweight `< 18.5`
  - 🟢 Normal Weight `18.5 – 24.9`
  - 🟠 Overweight `25 – 29.9`
  - 🔴 Obese `≥ 30`
- Animated BMI value counting up to the result
- Smooth progress bar filling from 0–40 BMI scale
- Result card slides in from the bottom

### Persistence & Sharing
- Last calculation saved via **SharedPreferences** and restored on next launch
- Share result to any app via Android's native **Share Intent** (WhatsApp, Gmail, SMS, Drive, Teams, etc.)
- "What is BMI?" info dialog for users who want context



## ⚙️ Tech Stack

| Layer | Technology | Role |
|---|---|---|
| Language | Java | Core application logic |
| UI | XML + Material Design Components | Layouts and interface |
| Architecture | Single Activity | Lightweight, no fragment overhead |
| Persistence | `SharedPreferences` | Save and restore last calculation |
| Sharing | Android Share Intent | Share result to any compatible app |
| Animations | `ValueAnimator` | Counting animation + progress bar fill |

> Zero third-party libraries. Everything is built on stock Android - part of the challenge was seeing how much polish you can get from the platform alone.



## 📱 Screenshots

<div align="center">

| Launch | Home | Result |
|:---:|:---:|:---:|
| <img src="./ScreenShots/Launch.png" width="200"/> | <img src="./ScreenShots/Home.png" width="200"/> | <img src="./ScreenShots/Result.png" width="200"/> |

| Share | | |
|:---:|:---:|:---:|
| <img src="./ScreenShots/Share.png" width="200"/> | | |

</div>



## 📂 Project Structure

```
BMIBuddy/
├── app/
│   └── src/
│       └── main/
│           ├── java/               # Application source code
│           ├── res/                # Layouts, drawables, strings
│           └── AndroidManifest.xml
├── ScreenShots/                    # App preview images
├── README.md
└── LICENSE
```



## 🚀 Getting Started

**Clone the repo**

```bash
git clone https://github.com/Sanchet237/BMIBuddy.git
```

**Open in Android Studio**

1. Launch Android Studio (Hedgehog or newer)
2. Go to **File → Open** and select the cloned directory
3. Wait for Gradle sync to finish
4. Hit Run on an emulator or physical device (API 21+)

**Requirements**

- Android Studio Hedgehog+
- Android SDK 21+
- Java 8+



## 👨‍💻 Author

<div align="center">

<img src="./ScreenShots/BMI Logo.png" width="140" alt="BMI Buddy Logo"/>

Made with ❤️ by <a href="https://github.com/Sanchet237"><strong>Sanchet Kolekar</strong></a>

*If BMI Buddy replaced that ad-riddled health app on your phone, a ⭐ goes a long way.*
<br/>

[![GitHub](https://img.shields.io/badge/GitHub-Profile-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Sanchet237)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/sanchet-kolekar-613916331/)
[![Instagram](https://img.shields.io/badge/Instagram-Follow-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/sanchetkolekar)
[![X](https://img.shields.io/badge/X-Follow-000000?style=for-the-badge&logo=x&logoColor=white)](https://x.com/Sanchet_237)
[![Gmail](https://img.shields.io/badge/Gmail-Contact-EA4335?style=for-the-badge&logo=gmail&logoColor=white)](mailto:sanchetkolekar.07@gmail.com)

<br/>

</div>