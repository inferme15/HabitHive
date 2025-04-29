package com.habithive.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.databinding.ItemGoalBinding
import com.habithive.app.model.Goal

class GoalAdapter(
    private val goals: List<Goal>,
    private val onGoalCheckedListener: (Goal, Boolean) -> Unit
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {
    
    inner class GoalViewHolder(val binding: ItemGoalBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        
        with(holder.binding) {
            textTitle.text = goal.title
            textDescription.text = goal.description
            textDuration.text = goal.duration
            textTargets.text = "Target: ${goal.targetPoints} points / ${goal.targetCalories} calories"
            
            // Set shared status
            if (goal.shared) {
                textShared.visibility = ViewGroup.VISIBLE
            } else {
                textShared.visibility = ViewGroup.GONE
            }
            
            // Set checkbox state without triggering listener
            checkboxCompleted.setOnCheckedChangeListener(null)
            checkboxCompleted.isChecked = goal.completed
            
            // Set up checkbox listener
            checkboxCompleted.setOnCheckedChangeListener { _, isChecked ->
                onGoalCheckedListener(goal, isChecked)
            }
        }
    }
    
    override fun getItemCount(): Int = goals.size
}
