package com.habithive.app.utils

import kotlin.random.Random

/**
 * Utility class for generating motivational quotes based on different categories.
 * Provides quotes for fitness, health, consistency, motivation, and general wellness.
 */
object QuotesUtil {

    // Categories of quotes
    enum class QuoteCategory {
        FITNESS,
        HEALTH,
        CONSISTENCY,
        MOTIVATION,
        MINDFULNESS,
        GENERAL
    }

    // Collection of fitness quotes
    private val fitnessQuotes = listOf(
        "Take care of your body. It's the only place you have to live. - Jim Rohn",
        "The only bad workout is the one that didn't happen. - Unknown",
        "Your health is an investment, not an expense. - Unknown",
        "Fitness is not about being better than someone else. It's about being better than you used to be. - Khloe Kardashian",
        "Exercise is king. Nutrition is queen. Put them together and you've got a kingdom. - Jack LaLanne",
        "The difference between trying and achieving is discipline. - Unknown",
        "The hardest lift of all is lifting your butt off the couch. - Unknown",
        "You don't have to be extreme, just consistent. - Unknown",
        "The only way to define your limits is by going beyond them. - Arthur C. Clarke",
        "Every workout counts, even when motivation doesn't strike. - Unknown"
    )

    // Collection of health quotes
    private val healthQuotes = listOf(
        "Health is a state of complete harmony of the body, mind, and spirit. - B.K.S. Iyengar",
        "The greatest wealth is health. - Virgil",
        "Let food be thy medicine and medicine be thy food. - Hippocrates",
        "Health is not valued until sickness comes. - Thomas Fuller",
        "Take care of your mind, your body will thank you. Take care of your body, your mind will thank you. - Debbie Hampton",
        "Your body deserves the best nutrition you can provide it. - Unknown",
        "A healthy outside starts from the inside. - Robert Urich",
        "Sleep is that golden chain that ties health and our bodies together. - Thomas Dekker",
        "He who has health has hope, and he who has hope has everything. - Arabian Proverb",
        "Water is the most neglected nutrient in your diet but one of the most vital. - Kelly Barton"
    )

    // Collection of consistency quotes
    private val consistencyQuotes = listOf(
        "Small daily improvements over time lead to stunning results. - Robin Sharma",
        "Consistency is what transforms average into excellence. - Unknown",
        "It's not what we do once in a while that shapes our lives, but what we do consistently. - Tony Robbins",
        "We are what we repeatedly do. Excellence, then, is not an act, but a habit. - Aristotle",
        "Long-term consistency trumps short-term intensity. - Bruce Lee",
        "Success isn't always about greatness. It's about consistency. Consistent hard work leads to success. Greatness will come. - Dwayne Johnson",
        "Motivation gets you going, but discipline keeps you growing. - John C. Maxwell",
        "The secret to getting ahead is getting started. The secret to getting started is breaking complex overwhelming tasks into small manageable tasks, then starting on the first one. - Mark Twain",
        "Consistency beats talent when talent doesn't work hard. - Unknown",
        "Daily disciplines compound over time. The small things you do each day add up to big results. - Unknown"
    )

    // Collection of motivation quotes
    private val motivationQuotes = listOf(
        "The difference between try and triumph is just a little umph! - Marvin Phillips",
        "Don't watch the clock; do what it does. Keep going. - Sam Levenson",
        "Success is walking from failure to failure with no loss of enthusiasm. - Winston Churchill",
        "It does not matter how slowly you go as long as you do not stop. - Confucius",
        "Believe you can and you're halfway there. - Theodore Roosevelt",
        "The past cannot be changed. The future is yet in your power. - Mary Pickford",
        "Don't count the days, make the days count. - Muhammad Ali",
        "Your only limit is you. - Unknown",
        "The harder you work for something, the greater you'll feel when you achieve it. - Unknown",
        "Dreams don't work unless you do. - John C. Maxwell"
    )

    // Collection of mindfulness quotes
    private val mindfulnessQuotes = listOf(
        "The present moment is the only moment available to us, and it is the door to all moments. - Thich Nhat Hanh",
        "Mindfulness isn't difficult, we just need to remember to do it. - Sharon Salzberg",
        "Be where you are, not where you think you should be. - Unknown",
        "In today's rush, we all think too much, seek too much, want too much and forget about the joy of just being. - Eckhart Tolle",
        "The best way to capture moments is to pay attention. This is how we cultivate mindfulness. - Jon Kabat-Zinn",
        "Wherever you are, be there totally. - Eckhart Tolle",
        "Mindfulness is a way of befriending ourselves and our experience. - Jon Kabat-Zinn",
        "The mind is everything. What you think you become. - Buddha",
        "Be happy in the moment, that's enough. Each moment is all we need, not more. - Mother Teresa",
        "Look past your thoughts, so you may drink the pure nectar of This Moment. - Rumi"
    )

    // Collection of general wellness quotes
    private val generalQuotes = listOf(
        "The groundwork for all happiness is good health. - Leigh Hunt",
        "Your body hears everything your mind says. - Naomi Judd",
        "You can't pour from an empty cup. Take care of yourself first. - Unknown",
        "Happiness is the highest form of health. - Dalai Lama",
        "Health and cheerfulness naturally beget each other. - Joseph Addison",
        "Wellness is the complete integration of body, mind, and spirit. - Greg Anderson",
        "Almost everything will work again if you unplug it for a few minutes, including you. - Anne Lamott",
        "When you take care of yourself, you're a better person for others. When you feel good about yourself, you treat others better. - Solange Knowles",
        "Self-care is giving the world the best of you, instead of what's left of you. - Katie Reed",
        "Rest when you're weary. Refresh and renew yourself, your body, your mind, your spirit. Then get back to work. - Ralph Marston"
    )

    /**
     * Get a random quote from a specific category
     * @param category The category to choose the quote from
     * @return A random quote as a string
     */
    fun getRandomQuote(category: QuoteCategory = QuoteCategory.GENERAL): String {
        val quotes = when (category) {
            QuoteCategory.FITNESS -> fitnessQuotes
            QuoteCategory.HEALTH -> healthQuotes
            QuoteCategory.CONSISTENCY -> consistencyQuotes
            QuoteCategory.MOTIVATION -> motivationQuotes
            QuoteCategory.MINDFULNESS -> mindfulnessQuotes
            QuoteCategory.GENERAL -> generalQuotes
        }
        
        return quotes[Random.nextInt(quotes.size)]
    }
    
    /**
     * Get a random quote from any category
     * @return A random quote as a string
     */
    fun getRandomQuoteFromAnyCategory(): String {
        val allCategories = QuoteCategory.values()
        val randomCategory = allCategories[Random.nextInt(allCategories.size)]
        return getRandomQuote(randomCategory)
    }
    
    /**
     * Get a suitable quote for a specific goal based on its characteristics
     * @param goalTitle The title of the goal to match with an appropriate quote
     * @param goalDescription The description of the goal for additional context
     * @return A contextually appropriate motivational quote
     */
    fun getQuoteForGoal(goalTitle: String, goalDescription: String): String {
        // Convert to lowercase for easier keyword matching
        val titleLower = goalTitle.lowercase()
        val descLower = goalDescription.lowercase()
        
        // Choose category based on goal keywords
        val category = when {
            containsAnyOf(titleLower, descLower, listOf("run", "cardio", "gym", "workout", "exercise", "train", "muscle", "lift", "strength")) -> 
                QuoteCategory.FITNESS
                
            containsAnyOf(titleLower, descLower, listOf("eat", "diet", "nutrition", "food", "weight", "healthy", "vegetable", "fruit", "protein")) -> 
                QuoteCategory.HEALTH
                
            containsAnyOf(titleLower, descLower, listOf("daily", "routine", "habit", "regular", "consistent", "streak", "discipline")) -> 
                QuoteCategory.CONSISTENCY
                
            containsAnyOf(titleLower, descLower, listOf("achieve", "success", "challenge", "complete", "goal", "target", "accomplish")) -> 
                QuoteCategory.MOTIVATION
                
            containsAnyOf(titleLower, descLower, listOf("meditate", "breathe", "calm", "stress", "anxiety", "mental", "mind", "focus", "balance")) -> 
                QuoteCategory.MINDFULNESS
                
            else -> QuoteCategory.GENERAL
        }
        
        return getRandomQuote(category)
    }
    
    /**
     * Helper method to check if any of the keywords exist in the goal text
     * @param title The goal title to check
     * @param description The goal description to check
     * @param keywords List of keywords to look for
     * @return True if any keyword is found
     */
    private fun containsAnyOf(title: String, description: String, keywords: List<String>): Boolean {
        return keywords.any { keyword -> 
            title.contains(keyword) || description.contains(keyword)
        }
    }
}