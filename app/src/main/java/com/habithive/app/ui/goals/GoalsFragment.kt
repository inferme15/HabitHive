package com.habithive.app.ui.goals

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.habithive.app.R
import com.habithive.app.adapters.GoalAdapter
import com.habithive.app.model.Goal
import com.habithive.app.ui.goals.add.AddGoalActivity

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

        recyclerView = root.findViewById(R.id.recycler_goals)
        emptyTextView = root.findViewById(R.id.text_empty_goals)
        progressBar = root.findViewById(R.id.progress_goals)
        addButton = root.findViewById(R.id.fab_add_goal)

        recyclerView.layoutManager = LinearLayoutManager(context)

        goalsViewModel = ViewModelProvider(this)[GoalsViewModel::class.java]

        goalsViewModel.goals.observe(viewLifecycleOwner) { goals ->
            updateGoalsList(goals)
        }

        goalsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        goalsViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        addButton.setOnClickListener {
            val intent = Intent(context, AddGoalActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        goalsViewModel.loadGoals()
    }

    private fun updateGoalsList(goals: List<Goal>) {
        if (goals.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE

            val adapter = GoalAdapter(goals) { selectedGoal ->
                val options = arrayOf("Share", "Delete")
                AlertDialog.Builder(requireContext())
                    .setTitle("Goal Options")
                    .setItems(options) { _, which ->
                        when (which) {
                            0 -> shareGoal(selectedGoal)
                            1 -> confirmDeleteGoal(selectedGoal)
                        }
                    }
                    .show()
            }

            recyclerView.adapter = adapter
        }
    }

    private fun confirmDeleteGoal(goal: Goal) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Goal")
            .setMessage("Are you sure you want to delete this goal?")
            .setPositiveButton("Yes") { _, _ ->
                goalsViewModel.deleteGoal(goal)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun shareGoal(goal: Goal) {
        val shareText = """
        🎯 Goal: ${goal.title}
        🔥 Progress: ${goal.getProgressPercentage()}%
        ✅ ${goal.currentCalories}/${goal.targetCalories} calories burned
        💪 ${goal.currentPoints}/${goal.targetPoints} points earned
        📅 Time left: ${goal.getRemainingDays()} day(s)
    """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share your goal via"))
    }


}
