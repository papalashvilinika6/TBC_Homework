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

        btnNext()
        btnBack()

    }

    private fun emailIsValid(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun btnBack() {
        binding.iBtnBackId.setOnClickListener {
            finish()
        }
    }

    private fun btnNext() {
        binding.iBrnNextId.setOnClickListener {
            val email = binding.etEmailId.text.toString().trim()
            val password = binding.etPasswordId.text.toString().trim()

            if(!isValid(mail = email, pass = password)) return@setOnClickListener

            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("emailAdress", email)
            intent.putExtra("userPassword", password)
            startActivity(intent)

        }
    }

    private fun isValid(mail: String, pass: String) : Boolean {
        if(mail.isEmpty() || pass.isEmpty()){
            Snackbar.make(binding.root, "Fill All Fields", Snackbar.LENGTH_SHORT).show()
            return false
        }else if(!emailIsValid(email = mail)) {
            Snackbar.make(binding.root, "Enter Real Email", Snackbar.LENGTH_SHORT).show()
            return false
        }else {
            return true
        }
    }

}