package com.habithive.app.utils

import android.view.View
import android.widget.TextView
import com.habithive.app.model.Goal
import kotlin.random.Random

/**
 * Helper class for displaying motivational quotes in the app.
 * Provides functionality to extract quotes from goals and display them in various UI components.
 */
object QuoteDisplayHelper {

    /**
     * Constants for quote display
     */
    private const val QUOTE_PREFIX = "\"" 
    private const val QUOTE_SUFFIX = "\""
    
    /**
     * Displays the inspirational quote from a goal in a TextView
     * @param goal The goal containing the quote
     * @param textView The TextView to display the quote in
     * @return True if a quote was displayed, false otherwise
     */
    fun displayQuoteFromGoal(goal: Goal, textView: TextView): Boolean {
        if (goal.inspirationalQuote.isNotEmpty()) {
            textView.text = goal.inspirationalQuote
            textView.visibility = View.VISIBLE
            return true
        } else {
            textView.visibility = View.GONE
            return false
        }
    }
    
    /**
     * Get a random quote from a list of goals with quotes
     * @param goals List of goals that may contain inspirational quotes
     * @return A randomly selected quote, or null if no quotes are available
     */
    fun getRandomQuoteFromGoals(goals: List<Goal>): String? {
        val goalsWithQuotes = goals.filter { it.inspirationalQuote.isNotEmpty() }
        
        return if (goalsWithQuotes.isNotEmpty()) {
            val randomGoal = goalsWithQuotes[Random.nextInt(goalsWithQuotes.size)]
            randomGoal.inspirationalQuote
        } else {
            null
        }
    }
    
    /**
     * Format a quote for display by adding quotation marks if not already present
     * @param quote The original quote text
     * @return Formatted quote with proper quotation marks
     */
    fun formatQuoteForDisplay(quote: String): String {
        // Skip if already formatted or empty
        if (quote.isEmpty() || (quote.startsWith(QUOTE_PREFIX) && quote.contains("\""))) {
            return quote
        }
        
        val parts = quote.split(" - ", limit = 2)
        return if (parts.size == 2) {
            "$QUOTE_PREFIX${parts[0].trim()}$QUOTE_SUFFIX - ${parts[1].trim()}"
        } else {
            "$QUOTE_PREFIX$quote$QUOTE_SUFFIX"
        }
    }
}