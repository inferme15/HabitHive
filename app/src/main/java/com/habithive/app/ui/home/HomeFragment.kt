package com.habithive.app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.habithive.app.R
import com.habithive.app.databinding.FragmentHomeBinding
import com.habithive.app.model.Achievement
import com.habithive.app.data.model.Exercise
import com.habithive.app.model.Goal
import com.habithive.app.utils.QuoteDisplayHelper
import com.habithive.app.utils.QuotesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var achievementListener: ListenerRegistration? = null
    private var exerciseListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // ✅ Correct place to avoid duplication
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupQuoteWidget()
        resetScoresIfNeeded()
        listenForAchievements()
        listenForTodayExercises()
        setupWeeklyLineChart()
    }

    // ✅ Menu setup
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notification_settings -> {
                Toast.makeText(
                    requireContext(),
                    "Notification settings coming soon!",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.action_invite -> {
                shareAppLink()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareAppLink() {
        val shareText = "Join me on HabitHive to track and crush your daily goals! 🐝\n" +
                "Soon available on Google Play Store!"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "HabitHive App")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, "Invite friends via"))
    }

    private fun setupQuoteWidget() {
        val quoteText = binding.textQuote
        val quoteAuthor = binding.textQuoteAuthor
        val refreshButton = binding.buttonRefreshQuote

        loadAndDisplayQuote(quoteText, quoteAuthor)
        refreshButton.setOnClickListener {
            loadAndDisplayQuote(quoteText, quoteAuthor)
            Toast.makeText(requireContext(), R.string.quote_refresh_success, Toast.LENGTH_SHORT)
                .show()
        }
        quoteText.setOnClickListener {
            quoteText.maxLines = if (quoteText.maxLines == 2) 10 else 2
        }
    }

    private fun loadAndDisplayQuote(quoteText: TextView, quoteAuthor: TextView) {
        val userId = auth.currentUser?.uid ?: return
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val goals = loadUserGoals(userId)
                val quote = goals.randomOrNull()?.inspirationalQuote
                    ?: QuotesUtil.getRandomQuoteFromAnyCategory()
                displayFormattedQuote(quote, quoteText, quoteAuthor)
            } catch (e: Exception) {
                val fallback = QuotesUtil.getRandomQuote(QuotesUtil.QuoteCategory.MOTIVATION)
                displayFormattedQuote(fallback, quoteText, quoteAuthor)
            }
        }
    }

    private suspend fun loadUserGoals(userId: String): List<Goal> {
        return try {
            val snapshot = firestore.collection("goals")
                .whereEqualTo("userId", userId)
                .get().await()
            snapshot.documents.mapNotNull { it.toObject(Goal::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun displayFormattedQuote(
        fullQuote: String,
        quoteText: TextView,
        quoteAuthor: TextView
    ) {
        val parts = fullQuote.split(" - ", limit = 2)
        if (parts.size == 2) {
            val content =
                if (!parts[0].trim().startsWith("\"")) "\"${parts[0].trim()}\"" else parts[0].trim()
            quoteText.text = content
            quoteAuthor.text = "- ${parts[1].trim()}"
            quoteAuthor.visibility = View.VISIBLE
        } else {
            quoteText.text = QuoteDisplayHelper.formatQuoteForDisplay(fullQuote)
            quoteAuthor.visibility = View.GONE
        }
    }

    private fun resetScoresIfNeeded() {
        val prefs = requireContext().getSharedPreferences("score_reset_prefs", Context.MODE_PRIVATE)
        val lastDailyReset = prefs.getLong("last_daily_reset", 0)
        val lastWeeklyReset = prefs.getLong("last_weekly_reset", 0)
        val now = Calendar.getInstance()

        val todayMidnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (lastDailyReset < todayMidnight.timeInMillis) {
            resetFirestoreScore("dailyScore")
            prefs.edit().putLong("last_daily_reset", System.currentTimeMillis()).apply()
        }

        val mondayMidnight = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) add(Calendar.WEEK_OF_YEAR, -1)
        }

        if (lastWeeklyReset < mondayMidnight.timeInMillis && now.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            resetFirestoreScore("weeklyScore")
            prefs.edit().putLong("last_weekly_reset", System.currentTimeMillis()).apply()
        }
    }

    private fun resetFirestoreScore(scoreField: String) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("achievements").document(userId)
            .update(scoreField, 0)
    }

    private fun listenForAchievements() {
        val userId = auth.currentUser?.uid ?: return
        achievementListener = firestore.collection("achievements")
            .document(userId)
            .addSnapshotListener { snapshot, _ ->
                val achievement =
                    snapshot?.toObject(Achievement::class.java) ?: return@addSnapshotListener
                binding.textTotalPoints.text = achievement.totalPoints.toString()
                binding.textCaloriesBurned.text = achievement.caloriesBurned.toString()
                binding.textDailyScore.text = achievement.dailyScore.toString()
                binding.textWeeklyScore.text = achievement.weeklyScore.toString()
            }
    }

    private fun listenForTodayExercises() {
        val userId = auth.currentUser?.uid ?: return

        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val tomorrowStart = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        exerciseListener = firestore.collection("exercises")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", todayStart)
            .whereLessThan("date", tomorrowStart)
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.documents?.mapNotNull { it.toObject(Exercise::class.java) }
                    ?: return@addSnapshotListener
                val completed = list.count { it.completed }
                val total = list.size
                val calories = list.sumOf { it.caloriesBurned }
                val points = list.sumOf { it.pointsEarned }

                binding.textHabitsCount.text = total.toString()
                binding.textCompletedHabits.text = completed.toString()
                val rate = if (total > 0) (completed * 100 / total) else 0
                binding.progressCompletion.progress = rate
                binding.textCompletionRate.text = "$rate%"

                binding.textCaloriesBurned.text = calories.toString()
                binding.textTotalPoints.text = points.toString()
                binding.textDailyScore.text = points.toString()
            }
    }

    private fun setupWeeklyLineChart() {
        val userId = auth.currentUser?.uid ?: return
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val entries = mutableListOf<Entry>()
        val labels = mutableListOf<String>()
        val today = Calendar.getInstance()

        CoroutineScope(Dispatchers.Main).launch {
            for (i in 6 downTo 0) {
                calendar.timeInMillis = today.timeInMillis
                calendar.add(Calendar.DAY_OF_YEAR, -i)
                val start = calendar.time

                calendar.add(Calendar.DAY_OF_YEAR, 1)
                val end = calendar.time

                val snapshot = firestore.collection("exercises")
                    .whereEqualTo("userId", userId)
                    .whereGreaterThanOrEqualTo("date", start)
                    .whereLessThan("date", end)
                    .get().await()

                val dayPoints = snapshot.documents.sumOf {
                    it.toObject(Exercise::class.java)?.pointsEarned ?: 0
                }

                entries.add(Entry((6 - i).toFloat(), dayPoints.toFloat()))
                labels.add(
                    calendar.getDisplayName(
                        Calendar.DAY_OF_WEEK,
                        Calendar.SHORT,
                        Locale.getDefault()
                    ) ?: ""
                )
            }

            // ✅ SAFETY CHECK BEFORE USING BINDING
            if (!isAdded || _binding == null) return@launch

            val dataSet = LineDataSet(entries, "Points").apply {
                color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                valueTextSize = 10f
                lineWidth = 2f
                circleRadius = 4f
                setCircleColor(color)
            }

            binding.chartProgress.apply {
                data = LineData(dataSet)
                xAxis.apply {
                    granularity = 1f
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = IndexAxisValueFormatter(labels)
                    setDrawGridLines(false)
                }
                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
                invalidate()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        achievementListener?.remove()
        exerciseListener?.remove()
    }
}