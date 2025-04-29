package com.habithive.app.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.R
import com.habithive.app.databinding.ItemLeaderboardBinding
import com.habithive.app.model.LeaderboardUser

class LeaderboardAdapter(
    private val users: List<LeaderboardUser>,
    private val currentUserId: String
) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    
    inner class LeaderboardViewHolder(val binding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val user = users[position]
        val isCurrentUser = user.id == currentUserId
        
        with(holder.binding) {
            textRank.text = "#${user.rank}"
            textName.text = user.name
            textScore.text = "${user.score} pts"
            
            // Highlight current user
            if (isCurrentUser) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.colorHighlight))
                textName.setTypeface(null, Typeface.BOLD)
                textScore.setTypeface(null, Typeface.BOLD)
            } else {
                root.setBackgroundColor(ContextCompat.getColor(root.context, android.R.color.transparent))
                textName.setTypeface(null, Typeface.NORMAL)
                textScore.setTypeface(null, Typeface.NORMAL)
            }
            
            // Add medal icons for top 3
            when (user.rank) {
                1 -> {
                    imageRank.setImageResource(R.drawable.ic_medal_gold)
                    imageRank.visibility = ViewGroup.VISIBLE
                }
                2 -> {
                    imageRank.setImageResource(R.drawable.ic_medal_silver)
                    imageRank.visibility = ViewGroup.VISIBLE
                }
                3 -> {
                    imageRank.setImageResource(R.drawable.ic_medal_bronze)
                    imageRank.visibility = ViewGroup.VISIBLE
                }
                else -> {
                    imageRank.visibility = ViewGroup.GONE
                }
            }
        }
    }
    
    override fun getItemCount(): Int = users.size
}
