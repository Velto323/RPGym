package com.example.rpgym.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player WHERE userId = :userId LIMIT 1")
    suspend fun getPlayer(userId: String): PlayerEntity?

    @Upsert
    suspend fun upsertPlayer(player: PlayerEntity)

    @Query("DELETE FROM player WHERE userId = :userId")
    suspend fun deletePlayer(userId: String)
}
