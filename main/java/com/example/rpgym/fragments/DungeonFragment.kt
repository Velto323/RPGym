package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class DungeonFragment : Fragment() {

    private var monsterHp = 100
    private var round = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_dungeon, container, false)

        val tvRound = view.findViewById<TextView>(R.id.tvRound)
        val tvMonster = view.findViewById<TextView>(R.id.tvMonster)
        val tvHp = view.findViewById<TextView>(R.id.tvHp)
        val btnAttack = view.findViewById<Button>(R.id.btnAttack)

        var currentMonsterName = "Goblin"

        fun updateUI() {
            tvRound.text = "Runda: $round"
            tvMonster.text = currentMonsterName
            tvHp.text = "HP: $monsterHp"
        }

        fun spawnMonster() {

            val isBoss = PlayerData.defeatedMonsters % 10 == 9 &&
                    PlayerData.defeatedMonsters != 0

            monsterHp = if (isBoss) 300 else 100

            currentMonsterName = if (isBoss) {
                "🧨 Goblin (BOSS)"
            } else {
                "Goblin"
            }

            updateUI()
        }

        btnAttack.setOnClickListener {

            val damage = 10 + PlayerData.strengthLevel
            monsterHp -= damage

            if (monsterHp <= 0) {

                PlayerData.defeatedMonsters++

                val isBoss = PlayerData.defeatedMonsters % 10 == 0
                val xpReward = if (isBoss) 150 else 50

                PlayerData.addXp(xpReward)

                round++

                Toast.makeText(
                    requireContext(),
                    "Pokonałeś potwora! +$xpReward XP",
                    Toast.LENGTH_SHORT
                ).show()

                spawnMonster()

            } else {
                updateUI()
            }
        }

        spawnMonster()

        return view
    }
}