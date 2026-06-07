package com.example.rpgym

data class Monster(
    var name: String,
    var hp: Int,
    var strength: Int,
    var isBoss: Boolean = false
)