package com.habithive.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.R
import com.habithive.app.databinding.FragmentHomeBinding
import com.habithive.app.model.Achievement
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
        
        // Load user data and stats
        loadUserStats()
        
        // Setup progress chart
        setupProgressChart()
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
