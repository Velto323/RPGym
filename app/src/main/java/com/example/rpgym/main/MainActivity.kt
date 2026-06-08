package com.example.rpgym.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData
import com.example.rpgym.data.PlayerRepository
import com.example.rpgym.data.local.AppDatabase
import com.example.rpgym.databinding.ActivityMainBinding
import com.example.rpgym.dung.DungeonHubFragment
import com.example.rpgym.home.HomeFragment
import com.example.rpgym.fragments.PlayerFragment
import com.example.rpgym.quest.QuestFragment
import com.example.rpgym.fragments.TrainingFragment
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: PlayerRepository
    private lateinit var tvHp: TextView
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = SupabaseClient.client.auth.currentUserOrNull()
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        userId = currentUser.id

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = PlayerRepository(AppDatabase.getInstance(applicationContext))

        // =========================
        // HP BAR
        // =========================
        tvHp = findViewById(R.id.tvGlobalHp)

        fun updateHp() {
            tvHp.text = "HP: ${PlayerData.hp} / ${PlayerData.getCurrentMaxHp()}"
        }

        PlayerData.addListener { runOnUiThread { updateHp() } }

        // =========================
        // LOAD PLAYER DATA
        // =========================
        binding.bottomNav.visibility = View.GONE
        binding.loadingIndicator.visibility = View.VISIBLE

        lifecycleScope.launch {
            repository.loadPlayer(userId!!)

            updateHp()
            binding.loadingIndicator.visibility = View.GONE
            binding.bottomNav.visibility = View.VISIBLE
            show(HomeFragment())
        }

        // =========================
        // NAVIGATION
        // =========================
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> { show(HomeFragment()); true }
                R.id.nav_training -> { show(TrainingFragment()); true }
                R.id.nav_dungeon -> { show(DungeonHubFragment()); true }
                R.id.nav_quests -> { show(QuestFragment()); true }
                R.id.nav_player -> { show(PlayerFragment()); true }
                else -> false
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val uid = userId ?: return
        // Local Room save is synchronous on IO thread — guarantees survival across process death
        runBlocking(Dispatchers.IO) { repository.savePlayerLocal(uid) }
        // Best-effort cloud sync — acceptable to occasionally miss, updatedAt reconciles on next load
        lifecycleScope.launch { repository.savePlayer(uid, syncToCloud = true) }
    }

    private fun show(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
