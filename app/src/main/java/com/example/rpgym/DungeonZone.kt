package com.example.rpgym

data class DungeonZone(
    val name: String,
    val description: String,
    val mobName: String,
    val bossName: String,
    val mobHp: Int,
    val mobAttack: Int,
    val bossHp: Int,
    val bossAttack: Int,
    val requiredZone: Int
)