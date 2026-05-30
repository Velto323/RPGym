package com.example.rpgym

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rpgym.databinding.ActivityRegisterBinding
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    register(email, password)
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginTextView.setOnClickListener {
            finish()
        }
    }

    private fun register(email: String, password: String) {
        lifecycleScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                Toast.makeText(this@RegisterActivity, "Registration successful! Please check your email for verification.", Toast.LENGTH_LONG).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}