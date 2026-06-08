package com.example.rpgym.dung

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.Monster
import com.example.rpgym.data.MonsterRepository
import com.example.rpgym.data.PlayerData

class BattleFragment : Fragment() {

    private lateinit var monster: Monster

    private lateinit var tvRound: TextView
    private lateinit var tvMonster: TextView
    private lateinit var tvHp: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_battle, container, false)

        tvRound = view.findViewById(R.id.tvRound)
        tvMonster = view.findViewById(R.id.tvMonster)
        tvHp = view.findViewById(R.id.tvHp)

        spawnMonster()
        updateUI()

        view.findViewById<Button>(R.id.btnAttack).setOnClickListener {
            playerAttack()
        }

        return view
    }

    private fun spawnMonster() {
        monster = MonsterRepository.getMonster(
            PlayerData.currentZone,
            PlayerData.dungeonWave
        )
    }

    private fun playerAttack() {

        monster.hp -= PlayerData.getStrength()

        if (monster.hp <= 0) {
            onKill()
            return
        }

        monsterAttack()
        updateUI()
    }

    private fun monsterAttack() {

        PlayerData.hp -= monster.strength

        if (PlayerData.hp <= 0) {
            onDeath()
        }
    }

    private fun onDeath() {

        PlayerData.dungeonWave = 1

        spawnMonster()
        updateUI()

        AlertDialog.Builder(requireContext())
            .setTitle("💀 Zginąłeś")
            .setMessage("Wracasz do pierwszej fali lochu.")
            .setPositiveButton("OK", null)
            .show()

        PlayerData.notifyChange()
    }

    private fun onKill() {

        val zone = PlayerData.currentZone

        PlayerData.defeatedMonsters++

        if (monster.isBoss) {

            val firstKill = PlayerData.addBossKill(zone)

            if (firstKill) {

                AlertDialog.Builder(requireContext())
                    .setTitle("🎉 Boss pokonany")
                    .setMessage("Odblokowano nową lokację!")
                    .setPositiveButton("OK", null)
                    .show()
            }

            PlayerData.dungeonWave = 1

        } else {

            PlayerData.dungeonWave++
        }

        spawnMonster()
        updateUI()

        PlayerData.notifyChange()
    }

    private fun updateUI() {

        tvRound.text = "Fala: ${PlayerData.dungeonWave}"
        tvMonster.text = monster.name
        tvHp.text = "HP: ${monster.hp}"
    }
}