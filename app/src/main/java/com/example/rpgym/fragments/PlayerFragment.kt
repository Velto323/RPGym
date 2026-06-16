package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class PlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_player,
            container,
            false
        )

        // PODPIĘCIE UI
        val level = view.findViewById<TextView>(R.id.tvPlayerLevel)
        val xp = view.findViewById<TextView>(R.id.tvPlayerXp)
        val strength = view.findViewById<TextView>(R.id.tvStrength)
        val tasks = view.findViewById<TextView>(R.id.tvCompletedTasks)

        // WYŚWIETLENIE DANYCH
        level.text = "Poziom: ${PlayerData.level}"
        xp.text = "XP: ${PlayerData.xp}/100"
        tasks.text = "Questy ukończone: ${PlayerData.completedTasks}"

        return view
    }
}