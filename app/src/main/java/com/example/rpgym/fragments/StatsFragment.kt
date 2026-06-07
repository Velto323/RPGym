package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class StatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        view.findViewById<TextView>(R.id.tvStatsLevel).text = "Poziom: ${PlayerData.level}"
        view.findViewById<TextView>(R.id.tvStatsXp).text = "XP: ${PlayerData.xp}/100"
        view.findViewById<TextView>(R.id.tvStatsStrength).text = "Siła: ${PlayerData.strengthLevel}"
        view.findViewById<TextView>(R.id.tvStatsTasks).text = "Zadania: ${PlayerData.completedTasks}"
        view.findViewById<TextView>(R.id.tvStatsMonsters).text = "Potwory: ${PlayerData.defeatedMonsters}"
        view.findViewById<TextView>(R.id.tvStatsGold).text = "Złoto: ${PlayerData.gold}"
        view.findViewById<TextView>(R.id.tvStatsHealthPotions).text = "Mikstury zdrowia: ${PlayerData.healthPotions}"
        view.findViewById<TextView>(R.id.tvStatsStrengthPotions).text = "Mikstury siły: ${PlayerData.strengthPotions}"

        return view
    }
}
