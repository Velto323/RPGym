package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class TrainingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_training, container, false)

        // UI
        val levelText = view.findViewById<TextView>(R.id.tvStrengthLevel)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressStrength)

        val btnPushups = view.findViewById<Button>(R.id.btnPushups)
        val btnPullups = view.findViewById<Button>(R.id.btnPullups)
        val btnSquats = view.findViewById<Button>(R.id.btnSquats)

        // update UI function
        fun updateUI() {
            levelText.text = "Trening siłowy - poziom ${PlayerData.strengthLevel}"
            progressBar.progress = PlayerData.strengthXp
        }

        // ACTIONS
        btnPushups.setOnClickListener {
            PlayerData.addStrengthXp(10)
            updateUI()
        }

        btnPullups.setOnClickListener {
            PlayerData.addStrengthXp(15)
            updateUI()
        }

        btnSquats.setOnClickListener {
            PlayerData.addStrengthXp(8)
            updateUI()
        }

        // init
        updateUI()

        return view
    }
}