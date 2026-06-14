package com.example.rpgym.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.rpgym.ui.screens.LoginScreen
import com.example.rpgym.ui.theme.RPGymTheme

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RPGymTheme {
                LoginScreen(
                    onLoginSuccess = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    onNavigateToRegister = {
                        startActivity(Intent(this, RegisterActivity::class.java))
                    },
                )
            }
        }
    }
}
