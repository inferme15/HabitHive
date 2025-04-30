package com.habithive.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.R
import com.habithive.app.model.Goal
import java.text.SimpleDateFormat
import java.util.Locale

class GoalAdapter(
    private val goals: List<Goal>,
    private val onCompletionChange: (Goal, Boolean) -> Unit
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    override fun getItemCount() = goals.size

    inner class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_goal_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.text_goal_description)
        private val durationTextView: TextView = itemView.findViewById(R.id.text_goal_duration)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_goal)
        private val progressTextView: TextView = itemView.findViewById(R.id.text_goal_progress)
        private val targetPointsTextView: TextView = itemView.findViewById(R.id.text_goal_target_points)
        private val targetCaloriesTextView: TextView = itemView.findViewById(R.id.text_goal_target_calories)
        private val completedCheckbox: CheckBox = itemView.findViewById(R.id.checkbox_goal_completed)
        private val sharedLabel: TextView = itemView.findViewById(R.id.text_goal_shared)
        private val quoteTextView: TextView = itemView.findViewById(R.id.text_goal_quote)
        
        fun bind(goal: Goal) {
            titleTextView.text = goal.title
            descriptionTextView.text = goal.description
            
            // Format duration text
            val remainingDays = goal.getRemainingDays()
            durationTextView.text = when {
                goal.isExpired() -> "Expired"
                remainingDays == 1 -> "1 day left"
                else -> "$remainingDays days left"
            }
            
            // Set progress
            val progress = goal.getProgressPercentage()
            progressBar.progress = progress
            progressTextView.text = "$progress%"
            
            // Format targets
            targetPointsTextView.text = "${goal.currentPoints}/${goal.targetPoints} points"
            targetCaloriesTextView.text = "${goal.currentCalories}/${goal.targetCalories} calories"
            
            // Set completion status
            completedCheckbox.isChecked = goal.completed
            completedCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onCompletionChange(goal, isChecked)
            }
            
            // Show/hide shared label
            sharedLabel.visibility = if (goal.shared) View.VISIBLE else View.GONE
            
            // Show inspirational quote if available
            if (goal.inspirationalQuote.isNotEmpty()) {
                quoteTextView.text = goal.inspirationalQuote
                quoteTextView.visibility = View.VISIBLE
            } else {
                quoteTextView.visibility = View.GONE
            }
        }
    }
}