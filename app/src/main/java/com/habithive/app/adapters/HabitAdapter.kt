package com.habithive.app.adapters

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.R
import com.habithive.app.data.model.Exercise
import com.habithive.app.databinding.ItemHabitBinding
import java.text.SimpleDateFormat
import java.util.*

class HabitAdapter(
    private val exercises: List<Exercise>,
    private val onExerciseDelete: (Exercise) -> Unit
) : RecyclerView.Adapter<HabitAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        with(holder.binding) {
            textTitle.text = exercise.type
            textDescription.text = exercise.notes.ifEmpty { "No notes" }
            textType.text = "${exercise.durationMinutes} min"
            textFrequency.text = formatDate(exercise.date)
            textPoints.text = "${exercise.pointsEarned} pts"

            menuMore.setOnClickListener { view ->
                showPopupMenu(view, exercise)
            }
        }
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    private fun showPopupMenu(view: View, exercise: Exercise) {
        val popup = PopupMenu(view.context, view)
        MenuInflater(view.context).inflate(R.menu.main_menu, popup.menu)
        popup.menu.clear()
        popup.menu.add("Delete")

        popup.setOnMenuItemClickListener { item ->
            if (item.title == "Delete") {
                onExerciseDelete(exercise)
                true
            } else false
        }
        popup.show()
    }

    override fun getItemCount(): Int = exercises.size
}
