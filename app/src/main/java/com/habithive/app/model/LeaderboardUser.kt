package com.habithive.app.model

data class LeaderboardUser(
    val id: String,
    val name: String,
    val score: Int,
    var rank: Int
)
