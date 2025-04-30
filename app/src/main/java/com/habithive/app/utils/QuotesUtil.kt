package com.habithive.app.utils

/**
 * Utility class for motivational quotes related to fitness and goal setting.
 * Provides methods to get random quotes or quotes by category.
 */
object QuotesUtil {
    
    // Categories for quotes
    enum class QuoteCategory {
        FITNESS,
        GOAL_SETTING,
        PERSEVERANCE,
        MOTIVATION,
        GENERAL
    }
    
    // Data class to represent a quote with its author and categories
    data class Quote(
        val text: String,
        val author: String,
        val categories: Set<QuoteCategory> = setOf(QuoteCategory.GENERAL)
    )
    
    // List of motivational quotes for fitness and goal setting
    private val quotes = listOf(
        Quote(
            "The only bad workout is the one that didn't happen.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "Setting goals is the first step in turning the invisible into the visible.",
            "Tony Robbins",
            setOf(QuoteCategory.GOAL_SETTING)
        ),
        Quote(
            "The difference between the impossible and the possible lies in a person's determination.",
            "Tommy Lasorda",
            setOf(QuoteCategory.PERSEVERANCE, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "The hardest lift of all is lifting your butt off the couch.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "Your body can stand almost anything. It's your mind that you have to convince.",
            "Andrew Murphy",
            setOf(QuoteCategory.FITNESS, QuoteCategory.PERSEVERANCE)
        ),
        Quote(
            "A goal without a plan is just a wish.",
            "Antoine de Saint-Exupéry",
            setOf(QuoteCategory.GOAL_SETTING)
        ),
        Quote(
            "Success is the sum of small efforts, repeated day in and day out.",
            "Robert Collier",
            setOf(QuoteCategory.PERSEVERANCE, QuoteCategory.GOAL_SETTING)
        ),
        Quote(
            "Fall seven times, stand up eight.",
            "Japanese Proverb",
            setOf(QuoteCategory.PERSEVERANCE)
        ),
        Quote(
            "What seems impossible today will one day become your warm-up.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "The only place where success comes before work is in the dictionary.",
            "Vidal Sassoon",
            setOf(QuoteCategory.MOTIVATION, QuoteCategory.GOAL_SETTING)
        ),
        Quote(
            "You don't have to be great to start, but you have to start to be great.",
            "Zig Ziglar",
            setOf(QuoteCategory.MOTIVATION, QuoteCategory.GOAL_SETTING)
        ),
        Quote(
            "Strength does not come from the body. It comes from the will.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "Don't count the days, make the days count.",
            "Muhammad Ali",
            setOf(QuoteCategory.MOTIVATION, QuoteCategory.GENERAL)
        ),
        Quote(
            "The body achieves what the mind believes.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "The pain you feel today will be the strength you feel tomorrow.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.PERSEVERANCE)
        ),
        Quote(
            "Set your goals high, and don't stop till you get there.",
            "Bo Jackson",
            setOf(QuoteCategory.GOAL_SETTING, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "The harder you work for something, the greater you'll feel when you achieve it.",
            "Unknown",
            setOf(QuoteCategory.GOAL_SETTING, QuoteCategory.PERSEVERANCE)
        ),
        Quote(
            "Discipline is the bridge between goals and accomplishment.",
            "Jim Rohn",
            setOf(QuoteCategory.GOAL_SETTING, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "Your health is an investment, not an expense.",
            "Unknown",
            setOf(QuoteCategory.FITNESS, QuoteCategory.MOTIVATION)
        ),
        Quote(
            "The only way to achieve the impossible is to believe it is possible.",
            "Charles Kingsleigh",
            setOf(QuoteCategory.MOTIVATION, QuoteCategory.GOAL_SETTING)
        )
    )
    
    /**
     * Get a random quote from the list.
     * @return A random Quote object
     */
    fun getRandomQuote(): Quote {
        return quotes.random()
    }
    
    /**
     * Get a random quote from a specific category.
     * @param category The QuoteCategory to filter by
     * @return A random Quote from the specified category, or a random quote if none found
     */
    fun getRandomQuoteByCategory(category: QuoteCategory): Quote {
        val filteredQuotes = quotes.filter { it.categories.contains(category) }
        return if (filteredQuotes.isNotEmpty()) {
            filteredQuotes.random()
        } else {
            getRandomQuote()
        }
    }
    
    /**
     * Get a formatted quote string with text and author.
     * @param quote The Quote object to format
     * @return A formatted string in the form: "Quote text" - Author
     */
    fun getFormattedQuote(quote: Quote): String {
        return "\"${quote.text}\" - ${quote.author}"
    }
    
    /**
     * Get a random formatted quote string.
     * @return A random formatted quote string
     */
    fun getRandomFormattedQuote(): String {
        return getFormattedQuote(getRandomQuote())
    }
    
    /**
     * Get a random formatted quote string from a specific category.
     * @param category The QuoteCategory to filter by
     * @return A random formatted quote string from the specified category
     */
    fun getRandomFormattedQuoteByCategory(category: QuoteCategory): String {
        return getFormattedQuote(getRandomQuoteByCategory(category))
    }
}