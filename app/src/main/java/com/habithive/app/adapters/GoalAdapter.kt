package com.habithive.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.R
import com.habithive.app.model.Goal
import com.habithive.app.utils.QuoteDisplayHelper

class GoalAdapter(
    private val goals: List<Goal>,
    private val onLongPressAction: (Goal) -> Unit
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
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
        private val sharedLabel: TextView = itemView.findViewById(R.id.text_goal_shared)
        private val quoteTextView: TextView = itemView.findViewById(R.id.text_goal_quote)
        private val emojiImageView: ImageView = itemView.findViewById(R.id.image_goal_status)

        fun bind(goal: Goal) {
            titleTextView.text = goal.title
            descriptionTextView.text = goal.description

            val remainingDays = goal.getRemainingDays()
            durationTextView.text = when {
                goal.isExpired() -> "Expired"
                remainingDays == 1 -> "1 day left"
                else -> "$remainingDays days left"
            }

            val progress = goal.getProgressPercentage()
            progressBar.progress = progress
            progressTextView.text = "$progress%"

            targetPointsTextView.text = "${goal.currentPoints}/${goal.targetPoints} points"
            targetCaloriesTextView.text = "${goal.currentCalories}/${goal.targetCalories} calories"

            sharedLabel.visibility = if (goal.shared) View.VISIBLE else View.GONE

            if (goal.inspirationalQuote.isNotEmpty()) {
                quoteTextView.text = QuoteDisplayHelper.formatQuoteForDisplay(goal.inspirationalQuote)
                quoteTextView.visibility = View.VISIBLE
                quoteTextView.setOnClickListener {
                    quoteTextView.maxLines = if (quoteTextView.maxLines == 2) 10 else 2
                }
            } else {
                quoteTextView.visibility = View.GONE
            }

            emojiImageView.setImageResource(
                when {
                    goal.completed -> R.drawable.ic_happy
                    goal.isExpired() -> R.drawable.ic_sad
                    else -> R.drawable.ic_neutral
                }
            )

            itemView.setOnLongClickListener {
                onLongPressAction(goal)
                true
            }
        }
    }
}
