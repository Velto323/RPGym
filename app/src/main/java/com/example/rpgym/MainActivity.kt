package com.example.rpgym

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.rpgym.databinding.ActivityMainBinding
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Check if user is logged in
        val currentUser = SupabaseClient.client.auth.currentUserOrNull()
        if (currentUser == null) {
            // Not logged in, redirect to Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // 2. Setup UI
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Display user email
        binding.welcomeTextView.text = "Welcome, ${currentUser.email}"

        // 3. Handle Logout
        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signOut()
                    // Redirect to Login
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    // Handle error (e.g., show a Toast)
                }
            }
        }
    }
}