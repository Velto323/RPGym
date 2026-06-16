package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class HomeFragment : Fragment() {

    private lateinit var tvLevel: TextView
    private lateinit var tvXp: TextView
    private lateinit var btnQuest: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        tvLevel = view.findViewById(R.id.tvLevel)
        tvXp = view.findViewById(R.id.tvXp)
        btnQuest = view.findViewById(R.id.btnQuest)

        updateUI()

        btnQuest.setOnClickListener {

            PlayerData.addXp(20)
            PlayerData.completedTasks++

            updateUI()
        }

        return view
    }

    private fun updateUI() {
        tvLevel.text = "Level: ${PlayerData.level}"
        tvXp.text = "XP: ${PlayerData.xp}/100"
    }
}