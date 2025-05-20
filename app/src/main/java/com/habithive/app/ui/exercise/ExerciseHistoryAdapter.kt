package com.habithive.app.ui.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.R
import com.habithive.app.databinding.ItemExerciseHistoryBinding
import com.habithive.app.data.model.Exercise
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter for displaying exercise history items in a RecyclerView
 * Supports long-press deletion
 */
class ExerciseHistoryAdapter(
    private val onDeleteExercise: (Exercise) -> Unit
) : ListAdapter<Exercise, ExerciseHistoryAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    // Date format for display
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }

    inner class ExerciseViewHolder(private val binding: ItemExerciseHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            // Format date
            val formattedDate = dateFormat.format(exercise.date)

            // Set values to views
            binding.textExerciseType.text = exercise.type
            binding.textDate.text = formattedDate
            binding.textDuration.text = binding.root.context.getString(
                R.string.duration_value,
                exercise.durationMinutes
            )
            binding.textCalories.text = binding.root.context.getString(
                R.string.calories_value,
                exercise.caloriesBurned
            )
            binding.textPoints.text = binding.root.context.getString(
                R.string.points_value,
                exercise.pointsEarned
            )

            // Set notes if available
            if (exercise.notes.isNotEmpty()) {
                binding.textNotes.text = exercise.notes
                binding.textNotes.visibility = android.view.View.VISIBLE
                binding.textNotesLabel.visibility = android.view.View.VISIBLE
            } else {
                binding.textNotes.visibility = android.view.View.GONE
                binding.textNotesLabel.visibility = android.view.View.GONE
            }

            // Long press to delete
            binding.root.setOnLongClickListener {
                onDeleteExercise(exercise)
                true
            }
        }
    }

    /**
     * DiffCallback to efficiently update RecyclerView items
     */
    private class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }
    }
}
