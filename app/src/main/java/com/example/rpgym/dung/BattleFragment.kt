package com.example.rpgym.dung

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.rpgym.*
import com.example.rpgym.data.*

class BattleFragment : Fragment() {

    private lateinit var monster: Monster

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_battle, container, false)

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

        PlayerData.hp = PlayerData.maxHp
        PlayerData.dungeonWave = 1

        spawnMonster()
        updateUI()

        AlertDialog.Builder(requireContext())
            .setTitle("💀 ZGINĄŁEŚ")
            .setMessage("Wracasz do 1 fali.")
            .setPositiveButton("OK", null)
            .show()

        PlayerData.notifyChange()
    }

    private fun onKill() {

        val zone = PlayerData.currentZone
        val wasBoss = monster.isBoss

        PlayerData.defeatedMonsters++

        if (wasBoss) {

            val first = PlayerData.addBossKill(zone)

            if (first) {
                AlertDialog.Builder(requireContext())
                    .setTitle("🎉 BOSS!")
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
        view?.findViewById<TextView>(R.id.tvRound)?.text =
            "Fala: ${PlayerData.dungeonWave}"
    }
}