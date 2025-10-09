package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.iBrnNextId.setOnClickListener {
            val email = binding.etEmailId.text.toString().trim()
            val password = binding.etPasswordId.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()){
                Snackbar.make(binding.root, "Fill All Fields", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!emailIsValid(email)) {
                Snackbar.make(binding.root, "Enter Real Email", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("emailAdress", email)
            intent.putExtra("userPassword", password)
            startActivity(intent)

        }

        binding.iBtnBackId.setOnClickListener {
            finish()
        }

    }

    fun emailIsValid(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}