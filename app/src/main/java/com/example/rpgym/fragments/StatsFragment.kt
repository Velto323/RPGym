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

        val level = view.findViewById<TextView>(R.id.tvStatsLevel)
        val xp = view.findViewById<TextView>(R.id.tvStatsXp)
        val strength = view.findViewById<TextView>(R.id.tvStatsStrength)
        val tasks = view.findViewById<TextView>(R.id.tvStatsTasks)
        val monsters = view.findViewById<TextView>(R.id.tvStatsMonsters)

        level.text = "Poziom: ${PlayerData.level}"
        xp.text = "XP: ${PlayerData.xp}/100"
        strength.text = "Siła: ${PlayerData.strengthLevel}"
        tasks.text = "Zadania: ${PlayerData.completedTasks}"
        monsters.text = "Potwory: ${PlayerData.defeatedMonsters}"

        return view
    }
}