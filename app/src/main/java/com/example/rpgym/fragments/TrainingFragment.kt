package com.example.rpgym.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

class TrainingFragment : Fragment() {

    private lateinit var tvStrengthInfo: TextView
    private lateinit var tvStrengthXp: TextView
    private lateinit var progressStrength: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_training,
            container,
            false
        )

        tvStrengthInfo =
            view.findViewById(R.id.tvStrengthInfo)

        tvStrengthXp =
            view.findViewById(R.id.tvStrengthXp)

        progressStrength =
            view.findViewById(R.id.progressStrength)

        view.findViewById<Button>(R.id.btnPushups)
            .setOnClickListener {

                PlayerData.addStrengthXp(10)
                updateUI()
            }

        view.findViewById<Button>(R.id.btnPullups)
            .setOnClickListener {

                PlayerData.addStrengthXp(15)
                updateUI()
            }

        view.findViewById<Button>(R.id.btnRun)
            .setOnClickListener {

                PlayerData.addStrengthXp(5)
                updateUI()
            }

        view.findViewById<Button>(R.id.btnSquats)
            .setOnClickListener {

                PlayerData.addStrengthXp(8)
                updateUI()
            }

        view.findViewById<Button>(R.id.btnLifePotion)
            .setOnClickListener {

                PlayerData.activateLifePotion()

                showMessage(
                    "❤️ Eliksir Życia",
                    "+50 HP przez 1 godzinę\nMedytacja 50% wolniejsza"
                )

                updateUI()
            }

        view.findViewById<Button>(R.id.btnPowerPotion)
            .setOnClickListener {

                if (!PlayerData.activatePowerPotion()) {

                    showMessage(
                        "Brak XP",
                        "Potrzebujesz 100 XP siły."
                    )

                    return@setOnClickListener
                }

                showMessage(
                    "⚡ Eliksir Siły",
                    "+5 siły przez 1 godzinę"
                )

                updateUI()
            }

        updateUI()

        return view
    }

    private fun updateUI() {

        tvStrengthInfo.text =
            "Siła lvl ${PlayerData.strengthLevel}"

        tvStrengthXp.text =
            "${PlayerData.strengthXp}/100 XP"

        progressStrength.progress =
            PlayerData.strengthXp
    }

    private fun showMessage(
        title: String,
        message: String
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}