const express = require('express');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 5000;

// Serve static files
app.use(express.static(path.join(__dirname)));

// Create sample data for the preview
const sampleData = {
  user: {
    name: "Alex Johnson",
    email: "alex@example.com",
    level: 7,
    totalPoints: 3250,
    totalCalories: 5820,
    weight: 72,
    height: 175,
    streak: 14,
    joinDate: new Date(2024, 2, 15) // March 15, 2024
  },
  habits: [
    {
      id: "habit1",
      title: "Morning Run",
      description: "30 minute run every morning",
      type: "exercise",
      exerciseType: "running",
      frequency: 1, // daily
      points: 30,
      caloriesBurnedPerCompletion: 300,
      completions: 28,
      goal: 1,
      streak: 14
    },
    {
      id: "habit2",
      title: "Drink Water",
      description: "Drink 8 glasses of water",
      type: "water",
      frequency: 1, // daily
      points: 10,
      caloriesBurnedPerCompletion: 0,
      completions: 32,
      goal: 1,
      streak: 8
    },
    {
      id: "habit3",
      title: "Gym Workout",
      description: "Strength training session",
      type: "exercise",
      exerciseType: "weightlifting",
      frequency: 2, // weekly
      points: 50,
      caloriesBurnedPerCompletion: 450,
      completions: 18,
      goal: 3,
      streak: 6
    },
    {
      id: "habit4",
      title: "Meditation",
      description: "15 minutes of mindfulness meditation",
      type: "mindfulness",
      frequency: 1, // daily
      points: 15,
      caloriesBurnedPerCompletion: 10,
      completions: 24,
      goal: 1,
      streak: 5
    }
  ],
  achievements: [
    {
      id: "achievement1",
      title: "Consistent!",
      description: "Complete habits for 7 consecutive days",
      type: "streak",
      points: 100,
      threshold: 7,
      currentProgress: 14,
      isUnlocked: true
    },
    {
      id: "achievement2",
      title: "Beginner",
      description: "Complete any habit 10 times",
      type: "completion",
      points: 50,
      threshold: 10,
      currentProgress: 28,
      isUnlocked: true
    },
    {
      id: "achievement3",
      title: "Calorie Burner",
      description: "Burn 1000 calories",
      type: "calories",
      points: 150,
      threshold: 1000,
      currentProgress: 5820,
      isUnlocked: true
    },
    {
      id: "achievement4",
      title: "Habit Master",
      description: "Complete habits for 30 consecutive days",
      type: "streak",
      points: 500,
      threshold: 30,
      currentProgress: 14,
      isUnlocked: false
    }
  ]
};

// API Routes
app.get('/api/user', (req, res) => {
  res.json(sampleData.user);
});

app.get('/api/habits', (req, res) => {
  res.json(sampleData.habits);
});

app.get('/api/achievements', (req, res) => {
  res.json(sampleData.achievements);
});

// Create a simple HTML home page
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'index.html'));
});

// Start the server
app.listen(PORT, '0.0.0.0', () => {
  console.log(`HabitHive preview server running at http://0.0.0.0:${PORT}`);
});