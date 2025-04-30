package com.habithive.app.ui.goals

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.habithive.app.R
import com.habithive.app.adapters.GoalAdapter
import com.habithive.app.model.Goal
import com.habithive.app.ui.goals.add.AddGoalActivity
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GoalsFragment : Fragment() {

    private lateinit var goalsViewModel: GoalsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var addButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_goals, container, false)
        
        // Initialize views
        recyclerView = root.findViewById(R.id.recycler_goals)
        emptyTextView = root.findViewById(R.id.text_empty_goals)
        progressBar = root.findViewById(R.id.progress_goals)
        addButton = root.findViewById(R.id.fab_add_goal)
        
        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        
        // Initialize ViewModel
        goalsViewModel = ViewModelProvider(this).get(GoalsViewModel::class.java)
        
        // Observe goals data
        goalsViewModel.goals.observe(viewLifecycleOwner) { goals ->
            updateGoalsList(goals)
        }
        
        // Observe loading state
        goalsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        // Observe error messages
        goalsViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
        
        // Setup add button
        addButton.setOnClickListener {
            val intent = Intent(context, AddGoalActivity::class.java)
            startActivity(intent)
        }
        
        return root
    }
    
    override fun onResume() {
        super.onResume()
        // Reload goals when fragment becomes visible
        goalsViewModel.loadGoals()
    }
    
    private fun updateGoalsList(goals: List<Goal>) {
        if (goals.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
            
            val adapter = GoalAdapter(goals) { goal, completed ->
                goalsViewModel.updateGoalCompletion(goal, completed)
            }
            recyclerView.adapter = adapter
        }
    }
}