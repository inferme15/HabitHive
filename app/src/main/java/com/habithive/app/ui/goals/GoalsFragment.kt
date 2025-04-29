package com.habithive.app.ui.goals

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.adapters.GoalAdapter
import com.habithive.app.databinding.FragmentGoalsBinding
import com.habithive.app.model.Goal

class GoalsFragment : Fragment() {
    
    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var goalAdapter: GoalAdapter
    private val goalsList = mutableListOf<Goal>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Load goals
        loadGoals()
        
        // Setup Add Goal button
        binding.fabAddGoal.setOnClickListener {
            startActivity(Intent(requireContext(), AddGoalActivity::class.java))
        }
    }
    
    private fun setupRecyclerView() {
        goalAdapter = GoalAdapter(goalsList) { goal, isCompleted ->
            updateGoalCompletion(goal, isCompleted)
        }
        
        binding.recyclerGoals.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = goalAdapter
        }
    }
    
    private fun loadGoals() {
        val userId = auth.currentUser?.uid ?: return
        
        // Show loading
        binding.progressBar.visibility = View.VISIBLE
        
        firestore.collection("goals")
            .whereEqualTo("userId", userId)
            .orderBy("duration", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                binding.progressBar.visibility = View.GONE
                
                if (e != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    goalsList.clear()
                    for (document in snapshot.documents) {
                        val goal = document.toObject(Goal::class.java)
                        goal?.let { goalsList.add(it) }
                    }
                    
                    goalAdapter.notifyDataSetChanged()
                    
                    // Update empty state
                    if (goalsList.isEmpty()) {
                        binding.textEmpty.visibility = View.VISIBLE
                    } else {
                        binding.textEmpty.visibility = View.GONE
                    }
                }
            }
    }
    
    private fun updateGoalCompletion(goal: Goal, isCompleted: Boolean) {
        val goalRef = firestore.collection("goals").document(goal.id)
        
        // Update goal completion status
        goalRef.update("completed", isCompleted)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
