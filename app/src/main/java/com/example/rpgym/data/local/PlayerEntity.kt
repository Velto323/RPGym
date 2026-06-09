package com.example.rpgym.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "player")
@TypeConverters(PlayerConverters::class)
data class PlayerEntity(
    @PrimaryKey val userId: String,

    val level: Int,
    val xp: Int,

    val strengthLevel: Int,
    val strengthXp: Int,

    val hp: Int,
    val maxHp: Int,

    val gold: Int,

    val healthPotions: Int,
    val strengthPotions: Int,

    val completedTasks: Int,

    val currentZone: Int,
    val unlockedZone: Int,
    val dungeonWave: Int,

    val defeatedMonsters: Int,
    val defeatedBosses: Set<Int>,

    val powerPotionEndTime: Long,
    val lifePotionEndTime: Long,
    val meditationStartTime: Long?,
    val lastMeditationTick: Long,

    val updatedAt: Long
)
