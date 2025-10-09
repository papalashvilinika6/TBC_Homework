package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityFourthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.snackbar.Snackbar



class FourthActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFourthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        btnBack()
        btnSignUp()


    }

    private fun btnBack() {
        binding.iBtnBackId.setOnClickListener {
            finish()
        }
    }

    private fun btnSignUp() {
        binding.iBtnNextId.setOnClickListener {
            val email: String = intent.getStringExtra("emailAdress") ?: ""
            val password: String = intent.getStringExtra("userPassword") ?: ""
            val username = binding.etUsernameId.text.toString().trim()

            if(isValid(user = username)) return@setOnClickListener

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Snackbar.make(binding.root, "Account creation failed.", Snackbar.LENGTH_SHORT).show()
                    }

                }
        }
    }

    private fun isValid(user: String) : Boolean {
        if(user.isEmpty()) {
            Snackbar.make(binding.root, "Please Enter Username!", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
