package com.habithive.app.model

import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date

data class Habit(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = TYPE_EXERCISE,
    val frequency: Int = FREQUENCY_DAILY,
    val points: Int = 0,
    val caloriesBurnedPerCompletion: Int = 0,
    val createdAt: Timestamp = Timestamp.now(),
    val completions: List<Timestamp> = emptyList(),
    val goal: Int = 1,
    val reminderTime: String? = null,
    val reminderDays: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7),
    val completed: Boolean = false,
    val caloriesBurned: Int = 0 // ✅ Added default value
) {
    companion object {
        const val TYPE_EXERCISE = "exercise"
        const val TYPE_NUTRITION = "nutrition"
        const val TYPE_MINDFULNESS = "mindfulness"
        const val TYPE_SLEEP = "sleep"
        const val TYPE_WATER = "water"
        const val TYPE_CUSTOM = "custom"

        const val FREQUENCY_DAILY = 1
        const val FREQUENCY_WEEKLY = 2
        const val FREQUENCY_MONTHLY = 3

        const val EXERCISE_RUNNING = "running"
        const val EXERCISE_CYCLING = "cycling"
        const val EXERCISE_WEIGHTLIFTING = "weightlifting"
        const val EXERCISE_OTHER = "other"
    }

    fun isCompletedToday(): Boolean {
        val today = Calendar.getInstance()
        return completions.any { timestamp ->
            val completionDate = Calendar.getInstance()
            completionDate.time = timestamp.toDate()
            today.get(Calendar.YEAR) == completionDate.get(Calendar.YEAR) &&
                    today.get(Calendar.DAY_OF_YEAR) == completionDate.get(Calendar.DAY_OF_YEAR)
        }
    }

    fun isCompletedOnDate(date: Date): Boolean {
        val targetDate = Calendar.getInstance()
        targetDate.time = date
        return completions.any { timestamp ->
            val completionDate = Calendar.getInstance()
            completionDate.time = timestamp.toDate()
            targetDate.get(Calendar.YEAR) == completionDate.get(Calendar.YEAR) &&
                    targetDate.get(Calendar.DAY_OF_YEAR) == completionDate.get(Calendar.DAY_OF_YEAR)
        }
    }

    fun completionsThisWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val weekStart = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)

        val weekEnd = calendar.timeInMillis

        return completions.count { timestamp ->
            val time = timestamp.toDate().time
            time in weekStart..weekEnd
        }
    }

    fun completionsThisMonth(): Int {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        return completions.count { timestamp ->
            val completionDate = Calendar.getInstance()
            completionDate.time = timestamp.toDate()
            completionDate.get(Calendar.MONTH) == currentMonth &&
                    completionDate.get(Calendar.YEAR) == currentYear
        }
    }

    fun calculateCurrentStreak(): Int {
        if (completions.isEmpty()) return 0

        val sortedCompletions = completions.sortedBy { it.toDate() }
        val lastCompletionDate = sortedCompletions.last().toDate()
        val lastCompletionCalendar = Calendar.getInstance().apply {
            time = lastCompletionDate
        }

        val today = Calendar.getInstance()
        val isToday = today.get(Calendar.YEAR) == lastCompletionCalendar.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == lastCompletionCalendar.get(Calendar.DAY_OF_YEAR)

        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }
        val isYesterday = yesterday.get(Calendar.YEAR) == lastCompletionCalendar.get(Calendar.YEAR) &&
                yesterday.get(Calendar.DAY_OF_YEAR) == lastCompletionCalendar.get(Calendar.DAY_OF_YEAR)

        if (!isToday && !isYesterday) return 0

        var streak = 1
        val currentDate = lastCompletionCalendar.clone() as Calendar

        for (i in 1..999) {
            currentDate.add(Calendar.DAY_OF_YEAR, -1)
            val hasCompletion = sortedCompletions.any { timestamp ->
                val completionDate = Calendar.getInstance()
                completionDate.time = timestamp.toDate()
                completionDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                        completionDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)
            }
            if (hasCompletion) {
                streak++
            } else {
                break
            }
        }

        return streak
    }

    fun getCompletionPercentage(): Int {
        val targetCompletions = when (frequency) {
            FREQUENCY_DAILY -> 1
            FREQUENCY_WEEKLY, FREQUENCY_MONTHLY -> goal
            else -> 1
        }

        val actualCompletions = when (frequency) {
            FREQUENCY_DAILY -> if (isCompletedToday()) 1 else 0
            FREQUENCY_WEEKLY -> completionsThisWeek()
            FREQUENCY_MONTHLY -> completionsThisMonth()
            else -> 0
        }

        return ((actualCompletions.toFloat() / targetCompletions.toFloat()) * 100).toInt().coerceIn(0, 100)
    }

    fun calculateCaloriesBurned(): Int = completions.size * caloriesBurnedPerCompletion

    fun calculateTotalPoints(): Int = completions.size * points

    fun formatNextReminder(): String {
        if (reminderTime == null) return "No reminder set"

        val today = Calendar.getInstance()
        val dayOfWeek = today.get(Calendar.DAY_OF_WEEK)
        val adjustedDay = if (dayOfWeek == Calendar.SUNDAY) 7 else dayOfWeek - 1

        if (reminderDays.contains(adjustedDay)) {
            val currentTime = today.get(Calendar.HOUR_OF_DAY) * 60 + today.get(Calendar.MINUTE)
            val (hour, minute) = reminderTime.split(":").map { it.toInt() }
            val reminderMinutes = hour * 60 + minute
            if (currentTime < reminderMinutes) {
                return "Today at $reminderTime"
            }
        }

        var nextDay = adjustedDay
        var daysToAdd = 0
        do {
            nextDay = (nextDay % 7) + 1
            daysToAdd++
        } while (!reminderDays.contains(nextDay) && daysToAdd < 7)

        if (daysToAdd >= 7) return "No upcoming reminders"

        val dayNames = arrayOf("", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        return "${dayNames[nextDay]} at $reminderTime"
    }
}
