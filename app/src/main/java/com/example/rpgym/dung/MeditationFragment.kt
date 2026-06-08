package com.example.rpgym.dung

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

class MeditationFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var tvHp: TextView
    private lateinit var tvTimer: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_meditation, container, false)

        tvHp = view.findViewById(R.id.tvHp)
        tvTimer = view.findViewById(R.id.tvTimer)

        PlayerData.startMeditation()
        startLoop()

        return view
    }

    private fun startLoop() {

        handler.post(object : Runnable {
            override fun run() {

                PlayerData.tickMeditation()

                updateUI()

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateUI() {

        tvHp.text = "HP: ${PlayerData.hp} / ${PlayerData.maxHp}"

        val time = PlayerData.getTimeToFullHp()

        tvTimer.text = if (time > 0)
            "Do pełnego HP: ${time}s"
        else
            "HP pełne ✔"
    }
}