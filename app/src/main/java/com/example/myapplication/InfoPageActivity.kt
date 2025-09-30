package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivitySavedBinding

class InfoPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0)

        binding.emailId.append(" $email")
        binding.userNameId.append(" $username")
        binding.nameId.append(" $name")
        binding.ageId.append(" $age")

        againButton()

    }

    private fun againButton() {
        binding.againBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}