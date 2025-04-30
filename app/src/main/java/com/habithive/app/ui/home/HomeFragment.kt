package com.habithive.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.R
import com.habithive.app.databinding.FragmentHomeBinding
import com.habithive.app.model.Achievement
import com.habithive.app.model.Goal
import com.habithive.app.utils.QuoteDisplayHelper
import com.habithive.app.utils.QuotesUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Set up quote display
        setupQuoteWidget()
        
        // Load user data and stats
        loadUserStats()
        
        // Setup progress chart
        setupProgressChart()
    }
    
    private fun setupQuoteWidget() {
        val quoteCard = binding.root.findViewById<MaterialCardView>(R.id.card_quote)
        val quoteText = binding.root.findViewById<TextView>(R.id.text_quote)
        val quoteAuthor = binding.root.findViewById<TextView>(R.id.text_quote_author)
        val refreshButton = binding.root.findViewById<ImageButton>(R.id.button_refresh_quote)
        
        // Initial quote display
        loadAndDisplayQuote(quoteText, quoteAuthor)
        
        // Set up refresh button click listener
        refreshButton.setOnClickListener {
            loadAndDisplayQuote(quoteText, quoteAuthor)
            Toast.makeText(requireContext(), R.string.quote_refresh_success, Toast.LENGTH_SHORT).show()
        }
        
        // Click on quote text to show full quote
        quoteText.setOnClickListener {
            if (quoteText.maxLines == 2) {
                quoteText.maxLines = 10
            } else {
                quoteText.maxLines = 2
            }
        }
    }
    
    private fun loadAndDisplayQuote(quoteText: TextView, quoteAuthor: TextView) {
        val userId = auth.currentUser?.uid ?: return
        
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // First, try to get a quote from one of the user's goals
                val goals = loadUserGoals(userId)
                
                if (goals.isNotEmpty()) {
                    // Try to get a quote from goals
                    val randomGoalQuote = QuoteDisplayHelper.getRandomQuoteFromGoals(goals)
                    
                    if (randomGoalQuote != null) {
                        displayFormattedQuote(randomGoalQuote, quoteText, quoteAuthor)
                        return@launch
                    }
                }
                
                // If no goal quotes found, generate a new one
                val quote = QuotesUtil.getRandomQuoteFromAnyCategory()
                displayFormattedQuote(quote, quoteText, quoteAuthor)
                
            } catch (e: Exception) {
                // Fallback to a random quote in case of error
                val quote = QuotesUtil.getRandomQuote(QuotesUtil.QuoteCategory.MOTIVATION)
                displayFormattedQuote(quote, quoteText, quoteAuthor)
            }
        }
    }
    
    private suspend fun loadUserGoals(userId: String): List<Goal> {
        try {
            val goalsSnapshot = firestore.collection("goals")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                
            return goalsSnapshot.documents.mapNotNull { document ->
                val goal = document.toObject(Goal::class.java)
                goal?.copy(id = document.id)
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }
    
    private fun displayFormattedQuote(fullQuote: String, quoteText: TextView, quoteAuthor: TextView) {
        // Split the quote and author (if formatted as "Quote - Author")
        val parts = fullQuote.split(" - ", limit = 2)
        
        if (parts.size == 2) {
            // Display quote and author separately
            val quoteContent = parts[0].trim().let { 
                if (!it.startsWith("\"") && !it.endsWith("\"")) "\"$it\"" else it 
            }
            quoteText.text = quoteContent
            quoteAuthor.text = "- ${parts[1].trim()}"
            quoteAuthor.visibility = View.VISIBLE
        } else {
            // Just display the full quote
            quoteText.text = QuoteDisplayHelper.formatQuoteForDisplay(fullQuote)
            quoteAuthor.visibility = View.GONE
        }
    }
    
    private fun loadUserStats() {
        val userId = auth.currentUser?.uid ?: return
        
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Get user achievements
                val achievementDoc = firestore.collection("achievements")
                    .document(userId)
                    .get()
                    .await()
                
                val achievement = achievementDoc.toObject(Achievement::class.java)
                
                // Get habits count
                val habitsQuery = firestore.collection("habits")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                
                val habitsCount = habitsQuery.size()
                
                // Get completed habits count
                val completedHabitsQuery = firestore.collection("habits")
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("completed", true)
                    .get()
                    .await()
                
                val completedHabitsCount = completedHabitsQuery.size()
                
                // Update UI with stats
                achievement?.let {
                    binding.textTotalPoints.text = it.totalPoints.toString()
                    binding.textCaloriesBurned.text = it.caloriesBurned.toString()
                    binding.textDailyScore.text = it.dailyScore.toString()
                    binding.textWeeklyScore.text = it.weeklyScore.toString()
                }
                
                binding.textHabitsCount.text = habitsCount.toString()
                binding.textCompletedHabits.text = completedHabitsCount.toString()
                
                // Calculate completion rate
                if (habitsCount > 0) {
                    val completionRate = (completedHabitsCount.toFloat() / habitsCount.toFloat()) * 100
                    binding.progressCompletion.progress = completionRate.toInt()
                    binding.textCompletionRate.text = String.format("%.1f%%", completionRate)
                } else {
                    binding.progressCompletion.progress = 0
                    binding.textCompletionRate.text = "0%"
                }
                
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }
    
    private fun setupProgressChart() {
        val userId = auth.currentUser?.uid ?: return
        
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Get last 7 days of habits completion data
                val calendar = Calendar.getInstance()
                val entries = ArrayList<BarEntry>()
                
                // For simplicity, generate some sample data
                // In a real app, you'd query Firestore for each day's data
                for (i in 6 downTo 0) {
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    val dayScore = (10..50).random()
                    entries.add(BarEntry((6 - i).toFloat(), dayScore.toFloat()))
                }
                
                val dataSet = BarDataSet(entries, "Daily Score")
                dataSet.color = resources.getColor(R.color.colorPrimary, null)
                
                val data = BarData(dataSet)
                binding.chartProgress.data = data
                binding.chartProgress.description.isEnabled = false
                binding.chartProgress.legend.isEnabled = false
                binding.chartProgress.invalidate()
                
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
