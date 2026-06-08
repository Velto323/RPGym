package com.example.rpgym.data

object MonsterRepository {

    fun getMonster(zone: Int, round: Int): Monster {

        return when (zone) {

            0 -> if (round == 5) Monster("Król Goblinów", 100, 10, true)
            else Monster("Goblin", 10, 5, false)

            1 -> if (round == 5) Monster("Wilkołak", 120, 15, true)
            else Monster("Wilk", 12, 10, false)

            2 -> if (round == 5) Monster("Kamienny Troll", 140, 20, true)
            else Monster("Kamień", 14, 15, false)

            3 -> if (round == 5) Monster("Rycerz", 160, 25, true)
            else Monster("Giermek", 16, 20, false)

            4 -> if (round == 5) Monster("Królowa Pająków", 180, 30, true)
            else Monster("Pająk", 18, 25, false)

            5 -> if (round == 5) Monster("Nekromanta", 200, 35, true)
            else Monster("Zombie", 20, 30, false)

            6 -> if (round == 5) Monster("Wiedźma", 250, 40, true)
            else Monster("Szkielet", 25, 35, false)

            7 -> if (round == 5) Monster("Gryf", 300, 45, true)
            else Monster("Harpia", 30, 40, false)

            8 -> if (round == 5) Monster("Wampir", 400, 50, true)
            else Monster("Nietoperz", 40, 45, false)

            9 -> if (round == 5) Monster("Smok", 500, 55, true)
            else Monster("Wiwerna", 50, 50, false)

            else -> Monster("Goblin", 10, 5, false)
        }
    }
}