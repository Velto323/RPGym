object MonsterRepository {

    fun getMonster(zone: Int, round: Int): Monster {

        return when (zone) {

            0 -> goblinZone(round)
            1 -> wolfZone(round)
            2 -> stoneZone(round)
            3 -> knightZone(round)
            4 -> spiderZone(round)
            5 -> undeadZone(round)
            6 -> witchZone(round)
            7 -> gryphonZone(round)
            8 -> vampireZone(round)
            9 -> dragonZone(round)

            else -> goblinZone(round)
        }
    }

    private fun goblinZone(round: Int): Monster {
        return if (round == 5)
            Monster("Król Goblinów", 100, 10, true)
        else
            Monster("Goblin", 10, 5, false)
    }

    private fun wolfZone(round: Int): Monster {
        return if (round == 5)
            Monster("Wilkołak", 120, 15, true)
        else
            Monster("Wilk", 12, 10, false)
    }

    private fun stoneZone(round: Int): Monster {
        return if (round == 5)
            Monster("Kamienny Troll", 140, 20, true)
        else
            Monster("Magiczny Kamień", 14, 15, false)
    }

    private fun knightZone(round: Int): Monster {
        return if (round == 5)
            Monster("Obłędny Rycerz", 160, 25, true)
        else
            Monster("Pijany Giermek", 16, 20, false)
    }

    private fun spiderZone(round: Int): Monster {
        return if (round == 5)
            Monster("Królowa Pająków", 180, 30, true)
        else
            Monster("Pająk", 18, 25, false)
    }

    private fun undeadZone(round: Int): Monster {
        return if (round == 5)
            Monster("Nekromanta", 200, 35, true)
        else
            Monster("Zombie", 20, 30, false)
    }

    private fun witchZone(round: Int): Monster {
        return if (round == 5)
            Monster("Wiedźma", 250, 40, true)
        else
            Monster("Szkielet", 25, 35, false)
    }

    private fun gryphonZone(round: Int): Monster {
        return if (round == 5)
            Monster("Gryf", 300, 45, true)
        else
            Monster("Harpia", 30, 40, false)
    }

    private fun vampireZone(round: Int): Monster {
        return if (round == 5)
            Monster("Wampir", 400, 50, true)
        else
            Monster("Nietoperz", 40, 45, false)
    }

    private fun dragonZone(round: Int): Monster {
        return if (round == 5)
            Monster("Smok", 500, 55, true)
        else
            Monster("Wiwerna", 50, 50, false)
    }
}