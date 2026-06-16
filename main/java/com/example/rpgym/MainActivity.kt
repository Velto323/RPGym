package com.example.rpgym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rpgym.databinding.ActivityMainBinding
import com.example.rpgym.fragments.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // startowy ekran
        show(HomeFragment())

        // NAWIGACJA DOLNA
        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_home -> {
                    show(HomeFragment())
                    true
                }

                R.id.nav_training -> {
                    show(TrainingFragment())
                    true
                }

                R.id.nav_dungeon -> {
                    show(DungeonFragment())
                    true
                }

                R.id.nav_stats -> {
                    show(StatsFragment())
                    true
                }

                R.id.nav_player -> {
                    show(PlayerFragment())
                    true
                }

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