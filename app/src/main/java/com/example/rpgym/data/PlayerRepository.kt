package com.example.rpgym.data

import com.example.rpgym.data.local.AppDatabase
import com.example.rpgym.data.local.PlayerEntity
import com.example.rpgym.data.remote.PlayerDto
import com.example.rpgym.data.remote.PlayerRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepository(
    db: AppDatabase,
    private val remote: PlayerRemoteDataSource = PlayerRemoteDataSource()
) {
    private val dao = db.playerDao()

    private fun snapshot(userId: String): PlayerEntity = PlayerEntity(
        userId = userId,
        level = PlayerData.level,
        xp = PlayerData.xp,
        strengthLevel = PlayerData.strengthLevel,
        strengthXp = PlayerData.strengthXp,
        hp = PlayerData.hp,
        maxHp = PlayerData.maxHp,
        gold = PlayerData.gold,
        healthPotions = PlayerData.healthPotions,
        strengthPotions = PlayerData.strengthPotions,
        completedTasks = PlayerData.completedTasks,
        currentZone = PlayerData.currentZone,
        unlockedZone = PlayerData.unlockedZone,
        dungeonWave = PlayerData.dungeonWave,
        defeatedMonsters = PlayerData.defeatedMonsters,
        defeatedBosses = PlayerData.defeatedBosses.toSet(),
        powerPotionEndTime = PlayerData.powerPotionEndTime,
        lifePotionEndTime = PlayerData.lifePotionEndTime,
        meditationStartTime = PlayerData.meditationStartTime,
        lastMeditationTick = PlayerData.lastMeditationTick,
        updatedAt = System.currentTimeMillis()
    )

    private fun apply(entity: PlayerEntity) {
        PlayerData.level = entity.level
        PlayerData.xp = entity.xp
        PlayerData.strengthLevel = entity.strengthLevel
        PlayerData.strengthXp = entity.strengthXp
        PlayerData.hp = entity.hp
        PlayerData.maxHp = entity.maxHp
        PlayerData.gold = entity.gold
        PlayerData.healthPotions = entity.healthPotions
        PlayerData.strengthPotions = entity.strengthPotions
        PlayerData.completedTasks = entity.completedTasks
        PlayerData.currentZone = entity.currentZone
        PlayerData.unlockedZone = entity.unlockedZone
        PlayerData.dungeonWave = entity.dungeonWave
        PlayerData.defeatedMonsters = entity.defeatedMonsters
        PlayerData.defeatedBosses.clear()
        PlayerData.defeatedBosses.addAll(entity.defeatedBosses)
        PlayerData.powerPotionEndTime = entity.powerPotionEndTime
        PlayerData.lifePotionEndTime = entity.lifePotionEndTime
        PlayerData.meditationStartTime = entity.meditationStartTime
        PlayerData.lastMeditationTick = entity.lastMeditationTick
        PlayerData.notifyChange()
    }

    private fun PlayerEntity.toDto() = PlayerDto(
        userId = userId, level = level, xp = xp,
        strengthLevel = strengthLevel, strengthXp = strengthXp,
        hp = hp, maxHp = maxHp, gold = gold,
        healthPotions = healthPotions, strengthPotions = strengthPotions,
        completedTasks = completedTasks,
        currentZone = currentZone, unlockedZone = unlockedZone, dungeonWave = dungeonWave,
        defeatedMonsters = defeatedMonsters, defeatedBosses = defeatedBosses.toList(),
        powerPotionEndTime = powerPotionEndTime, lifePotionEndTime = lifePotionEndTime,
        meditationStartTime = meditationStartTime, lastMeditationTick = lastMeditationTick,
        updatedAt = updatedAt
    )

    private fun PlayerDto.toEntity() = PlayerEntity(
        userId = userId, level = level, xp = xp,
        strengthLevel = strengthLevel, strengthXp = strengthXp,
        hp = hp, maxHp = maxHp, gold = gold,
        healthPotions = healthPotions, strengthPotions = strengthPotions,
        completedTasks = completedTasks,
        currentZone = currentZone, unlockedZone = unlockedZone, dungeonWave = dungeonWave,
        defeatedMonsters = defeatedMonsters, defeatedBosses = defeatedBosses.toSet(),
        powerPotionEndTime = powerPotionEndTime, lifePotionEndTime = lifePotionEndTime,
        meditationStartTime = meditationStartTime, lastMeditationTick = lastMeditationTick,
        updatedAt = updatedAt
    )

    suspend fun loadPlayer(userId: String) = withContext(Dispatchers.IO) {
        val local = dao.getPlayer(userId)
        val remoteDto = runCatching { remote.fetchPlayer(userId) }.getOrNull()
        val remoteEntity = remoteDto?.toEntity()

        val winner: PlayerEntity = when {
            remoteEntity != null && local != null ->
                if (remoteEntity.updatedAt >= local.updatedAt) remoteEntity else local
            remoteEntity != null -> remoteEntity
            local != null -> local
            else -> snapshot(userId)
        }

        apply(winner)
        dao.upsertPlayer(winner)

        // push local winner to cloud if remote was absent or stale
        if (remoteEntity == null || winner.updatedAt > remoteEntity.updatedAt) {
            runCatching { remote.upsertPlayer(winner.toDto()) }
        }
    }

    suspend fun savePlayer(userId: String, syncToCloud: Boolean = true) = withContext(Dispatchers.IO) {
        val entity = snapshot(userId)
        dao.upsertPlayer(entity)
        if (syncToCloud) {
            runCatching { remote.upsertPlayer(entity.toDto()) }
        }
    }

    // Fast local-only save, safe to call with runBlocking from onStop
    suspend fun savePlayerLocal(userId: String) = withContext(Dispatchers.IO) {
        dao.upsertPlayer(snapshot(userId))
    }
}
