package com.example.rpgym.data.remote

import com.example.rpgym.main.SupabaseClient
import io.github.jan.supabase.postgrest.from

class PlayerRemoteDataSource {

    suspend fun fetchPlayer(userId: String): PlayerDto? =
        SupabaseClient.client.from("players")
            .select { filter { eq("user_id", userId) } }
            .decodeSingleOrNull<PlayerDto>()

    suspend fun upsertPlayer(dto: PlayerDto) {
        SupabaseClient.client.from("players").upsert(dto)
    }
}
