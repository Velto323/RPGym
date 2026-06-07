package com.example.rpgym.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.databinding.ActivityMainBinding
import com.example.rpgym.dung.DungeonFragment
import com.example.rpgym.home.HomeFragment
import com.example.rpgym.fragments.PlayerFragment
import com.example.rpgym.quest.QuestFragment
import com.example.rpgym.fragments.TrainingFragment
import io.github.jan.supabase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = SupabaseClient.client.auth.currentUserOrNull()
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        show(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> { show(HomeFragment()); true }
                R.id.nav_training -> { show(TrainingFragment()); true }
                R.id.nav_dungeon -> { show(DungeonFragment()); true }
                R.id.nav_quests -> { show(QuestFragment()); true }
                R.id.nav_player -> { show(PlayerFragment()); true }
                else -> false
            }
        }
    }

    private fun show(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}