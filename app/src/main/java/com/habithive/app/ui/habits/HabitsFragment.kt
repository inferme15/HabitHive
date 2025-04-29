package com.habithive.app.ui.habits

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.adapters.HabitAdapter
import com.habithive.app.databinding.FragmentHabitsBinding
import com.habithive.app.model.Habit
import com.habithive.app.utils.calculatePoints
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HabitsFragment : Fragment() {
    
    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var habitAdapter: HabitAdapter
    private val habitsList = mutableListOf<Habit>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Load habits
        loadHabits()
        
        // Setup Add Habit button
        binding.fabAddHabit.setOnClickListener {
            startActivity(Intent(requireContext(), AddHabitActivity::class.java))
        }
    }
    
    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(habitsList) { habit, isCompleted ->
            updateHabitCompletion(habit, isCompleted)
        }
        
        binding.recyclerHabits.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitAdapter
        }
    }
    
    private fun loadHabits() {
        val userId = auth.currentUser?.uid ?: return
        
        // Show loading
        binding.progressBar.visibility = View.VISIBLE
        
        firestore.collection("habits")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                binding.progressBar.visibility = View.GONE
                
                if (e != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    habitsList.clear()
                    for (document in snapshot.documents) {
                        val habit = document.toObject(Habit::class.java)
                        habit?.let { habitsList.add(it) }
                    }
                    
                    habitAdapter.notifyDataSetChanged()
                    
                    // Update empty state
                    if (habitsList.isEmpty()) {
                        binding.textEmpty.visibility = View.VISIBLE
                    } else {
                        binding.textEmpty.visibility = View.GONE
                    }
                }
            }
    }
    
    private fun updateHabitCompletion(habit: Habit, isCompleted: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: return@launch
                val habitRef = firestore.collection("habits").document(habit.id)
                
                // Update habit completion status
                habitRef.update("completed", isCompleted)
                
                if (isCompleted) {
                    // Calculate points
                    val points = calculatePoints(habit)
                    
                    // Update user achievements with points
                    val achievementRef = firestore.collection("achievements").document(userId)
                    
                    // Get current achievement data
                    val achievementDoc = achievementRef.get().await()
                    val totalPoints = achievementDoc.getLong("totalPoints")?.toInt() ?: 0
                    val caloriesBurned = achievementDoc.getLong("caloriesBurned")?.toInt() ?: 0
                    val dailyScore = achievementDoc.getLong("dailyScore")?.toInt() ?: 0
                    val weeklyScore = achievementDoc.getLong("weeklyScore")?.toInt() ?: 0
                    
                    // Update achievement data
                    achievementRef.update(
                        mapOf(
                            "totalPoints" to totalPoints + points,
                            "caloriesBurned" to caloriesBurned + habit.caloriesBurned,
                            "dailyScore" to dailyScore + points,
                            "weeklyScore" to weeklyScore + points
                        )
                    )
                }
                
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
