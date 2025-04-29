# HabitHive - Your Smart Habit Tracker

HabitHive is a mobile application designed to help users track their physical exercise and engage in a community-driven fitness experience. It allows users to log their workouts, monitor progress, and earn points based on their activities.

## Features

- **User Authentication**: Login, registration, and profile management using Firebase
- **Habit Tracking**: Create, update, and delete fitness habits
- **Gamification**: Points system, achievements, and leaderboards
- **Goals**: Set and track fitness goals with progress indicators
- **Community**: Social features including friend system and community challenges
- **Analytics**: Visual progress tracking and fitness insights
- **Notifications**: Reminders and achievement notifications

## Technology Stack

- **Frontend**: Android with Kotlin
- **Backend**: Firebase (Authentication, Firestore, Storage)
- **Architecture**: MVVM with Clean Architecture principles
- **Dependency Injection**: Hilt
- **UI Framework**: Jetpack Compose with Material Design
- **Asynchronous Programming**: Kotlin Coroutines & Flow

## Project Structure

```
HabitHive/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/habithive/app/
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
2. Set up Firebase project and add google-services.json to the app/ directory
3. Open the project in Android Studio
4. Build and run the application

## Troubleshooting

If you encounter package name issues during build, ensure:

1. The Firebase google-services.json file has the correct package name (com.example.habithive)
2. The namespace in app/build.gradle matches your code structure (com.habithive.app)
3. The package attribute is removed from AndroidManifest.xml

## Future Enhancements

Planned features for future releases:

- Advanced statistics and insights
- Habit recommendations based on user behavior
- Social challenges and group goals
- Integration with fitness tracking devices
- Offline mode for tracking without internet connection