package com.habithive.app.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val health: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,val shareGoal: Boolean = false // ✅ Added this field
)
