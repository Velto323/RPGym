package com.example.rpgym

data class Monster(
    val name: String,
    var hp: Int,
    val strength: Int,
    val isBoss: Boolean = false
)