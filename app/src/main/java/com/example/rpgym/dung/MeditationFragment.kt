package com.example.rpgym.dung

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

class MeditationFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())
    private var running = true

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

        PlayerData.addListener {
            updateUI()
        }

        loop()

        return view
    }

    private fun loop() {

        handler.post(object : Runnable {
            override fun run() {

                if (!running) return

                PlayerData.tickMeditation()

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateUI() {

        val hp = PlayerData.hp
        val max = PlayerData.getCurrentMaxHp()

        tvHp.text = "HP: $hp / $max"

        val t = PlayerData.getTimeToFullHp()

        tvTimer.text =
            if (t <= 0) "FULL HP ✔"
            else "Do pełnego HP: ${t}s"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        running = false
        handler.removeCallbacksAndMessages(null)
    }
}