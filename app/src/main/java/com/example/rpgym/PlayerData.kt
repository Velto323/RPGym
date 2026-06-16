package com.example.rpgym
//to dodane
object PlayerData {

    var level = 1
    var xp = 0

    var strengthLevel = 1
    var strengthXp = 0

    var completedTasks = 0
    var defeatedMonsters = 0

    fun addXp(amount: Int) {
        xp += amount
        while (xp >= 100) {
            xp -= 100
            level++
        }
    }

    fun addStrengthXp(amount: Int) {
        strengthXp += amount
        while (strengthXp >= 100) {
            strengthXp -= 100
            strengthLevel++
        }
    }
}