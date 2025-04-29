package com.habithive.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.databinding.ItemHabitBinding
import com.habithive.app.model.Habit

class HabitAdapter(
    private val habits: List<Habit>,
    private val onHabitCheckedListener: (Habit, Boolean) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    
    inner class HabitViewHolder(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        
        with(holder.binding) {
            textTitle.text = habit.title
            textDescription.text = habit.description
            textType.text = habit.type
            textFrequency.text = habit.frequency
            textPoints.text = "${habit.points} points"
            
            // Set checkbox state without triggering listener
            checkboxCompleted.setOnCheckedChangeListener(null)
            checkboxCompleted.isChecked = habit.completed
            
            // Set up checkbox listener
            checkboxCompleted.setOnCheckedChangeListener { _, isChecked ->
                onHabitCheckedListener(habit, isChecked)
            }
        }
    }
    
    override fun getItemCount(): Int = habits.size
}
