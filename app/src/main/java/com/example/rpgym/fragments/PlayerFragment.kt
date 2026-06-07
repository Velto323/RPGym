package com.example.rpgym.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.rpgym.main.LoginActivity
import com.example.rpgym.data.PlayerData
import com.example.rpgym.R
import com.example.rpgym.main.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class PlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        view.findViewById<TextView>(R.id.tvPlayerLevel).text = "Poziom: ${PlayerData.level}"
        view.findViewById<TextView>(R.id.tvPlayerXp).text = "XP: ${PlayerData.xp}/100"
        view.findViewById<TextView>(R.id.tvStrength).text = "Siła: ${PlayerData.strengthLevel}"
        view.findViewById<TextView>(R.id.tvCompletedTasks).text = "Questy ukończone: ${PlayerData.completedTasks}"

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signOut()
                } catch (_: Exception) {}
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

        return view
    }
}
