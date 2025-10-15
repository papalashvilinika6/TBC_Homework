package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Data.emailIsValid
import com.example.myapplication.Data.showSnackbar
import com.example.myapplication.Data.users
import com.example.myapplication.databinding.ActivityAddBinding
import com.google.android.material.snackbar.Snackbar


class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        btnAdd()
    }

    private fun btnAdd() {
        with(binding) {

            btnAddUsersId.setOnClickListener {
                val firstName = etFirstNameId.text.toString().trim()
                val lastName = etLastNameId.text.toString().trim()
                val age = etAgeId.text.toString().trim()
                val email = etEmailId.text.toString().trim()

                if (!allInputsValid(firstName,lastName,age,email)) return@setOnClickListener

                if (!emailIsValid(email = email)) {
                    Snackbar.make(root, getString(R.string.enter_real_email), Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (users.none { it.email == email }) {
                    val user = User(firstName, lastName, age.toIntOrNull(), email)
                    users.add(user)
                    sendToMain(true)
                } else {
                    sendToMain(false)
                }

            }
        }
    }

    private fun allInputsValid(firstName:String, lastName:String, age:String, email:String) : Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()) {
            showSnackbar(binding.root , getString(R.string.fields))
            return false
        }
        return true
    }

    private fun sendToMain(success: Boolean) {
        val intent = Intent(this@AddUserActivity, MainActivity::class.java)
        intent.putExtra("check", success)
        startActivity(intent)
    }

}
