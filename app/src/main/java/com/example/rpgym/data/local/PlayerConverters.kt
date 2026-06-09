package com.example.rpgym.data.local

import androidx.room.TypeConverter

class PlayerConverters {

    @TypeConverter
    fun fromBossSet(bosses: Set<Int>): String =
        bosses.joinToString(separator = ",")

    @TypeConverter
    fun toBossSet(raw: String): Set<Int> =
        if (raw.isBlank()) emptySet()
        else raw.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
}
