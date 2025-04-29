# HabitHive - Your Smart Habit Tracker

**HabitHive** is a Kotlin-based Android fitness habit tracker application with gamification and community features using Firebase integration.

![HabitHive Logo](generated-icon.png)

## Application Overview

HabitHive helps users build and maintain fitness habits through a gamified experience, tracking progress, awarding achievements, and providing social features for motivation.

### Key Features

- **Habit Tracking**: Create, track, and manage your daily fitness habits
- **Gamification**: Earn points, badges, and achievements as you complete habits
- **Community**: Compete with friends on leaderboards and share goals
- **Analytics**: Track progress with detailed statistics and insights
- **Personalization**: Customize your profile and habit preferences

## Technical Architecture

HabitHive is built with modern Android development practices:

- **Languages**: Kotlin, XML
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend**: Firebase (Authentication, Firestore)
- **Dependency Injection**: Hilt
- **UI Framework**: Jetpack Compose with Material Design
- **Asynchronous Programming**: Kotlin Coroutines & Flow

## Project Structure

The application follows a clean, modular structure:

```
HabitHive/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/habithive/
│   │   │   │   ├── analytics/          # Analytics and statistics tracking
│   │   │   │   ├── auth/               # Authentication related code
│   │   │   │   ├── data/               # Data models and repositories
│   │   │   │   ├── di/                 # Dependency injection modules
│   │   │   │   ├── domain/             # Business logic and use cases
│   │   │   │   ├── ui/                 # UI components and screens
│   │   │   │   │   ├── achievements/   # Achievements screen
│   │   │   │   │   ├── exercise/       # Exercise tracking screens
│   │   │   │   │   ├── goals/          # Goal setting and tracking
│   │   │   │   │   ├── home/           # Home dashboard
│   │   │   │   │   ├── leaderboard/    # Leaderboard screen
│   │   │   │   │   ├── profile/        # User profile
│   │   │   │   │   └── theme/          # App theming
│   │   │   │   └── utils/              # Utility functions
│   │   │   ├── res/                    # Android resources
│   │   │   │   ├── drawable/           # Images and vectors
│   │   │   │   ├── layout/             # XML layouts
│   │   │   │   ├── navigation/         # Navigation graphs
│   │   │   │   └── values/             # Strings, colors, styles
│   │   │   └── AndroidManifest.xml
│   │   └── test/                       # Unit tests
├── build.gradle                        # Project-level build config
├── gradle/                             # Gradle wrapper and config
└── settings.gradle                     # Project settings
```

## Gamification System

HabitHive implements a comprehensive gamification system to keep users motivated:

- **Points**: Earn points for completing habits and achieving milestones
- **Medals**: Gold, Silver, and Bronze medals based on streak lengths
- **Achievements**: Unlock special achievements for consistent performance
- **Levels**: Progress through levels as you accumulate points
- **Leaderboards**: Compare your progress with friends and the community

## Setup and Deployment

To run the HabitHive application:

1. Clone the repository
2. Set up Firebase project and add google-services.json
3. Build and run the application using Android Studio or Gradle

## Future Enhancements

Planned features for future releases:

- Advanced statistics and insights
- Habit recommendations based on user behavior
- Social challenges and group goals
- Integration with fitness tracking devices
- Offline mode for tracking without internet connection