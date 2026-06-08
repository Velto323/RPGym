package com.example.rpgym.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("user_id") val userId: String,

    val level: Int,
    val xp: Int,

    @SerialName("strength_level") val strengthLevel: Int,
    @SerialName("strength_xp") val strengthXp: Int,

    val hp: Int,
    @SerialName("max_hp") val maxHp: Int,

    val gold: Int,

    @SerialName("health_potions") val healthPotions: Int,
    @SerialName("strength_potions") val strengthPotions: Int,

    @SerialName("completed_tasks") val completedTasks: Int,

    @SerialName("current_zone") val currentZone: Int,
    @SerialName("unlocked_zone") val unlockedZone: Int,
    @SerialName("dungeon_wave") val dungeonWave: Int,

    @SerialName("defeated_monsters") val defeatedMonsters: Int,
    @SerialName("defeated_bosses") val defeatedBosses: List<Int>,

    @SerialName("power_potion_end_time") val powerPotionEndTime: Long,
    @SerialName("life_potion_end_time") val lifePotionEndTime: Long,
    @SerialName("meditation_start_time") val meditationStartTime: Long? = null,
    @SerialName("last_meditation_tick") val lastMeditationTick: Long,

    @SerialName("updated_at") val updatedAt: Long
)
