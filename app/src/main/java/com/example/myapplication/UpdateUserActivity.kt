package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Data.showSnackbar
import com.example.myapplication.Data.users
import com.example.myapplication.databinding.ActivityUpdateBinding

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        editTextImutable()
        btnUpdate()
        btnRemove()

    }

    private fun btnRemove() {
        binding.btnRemoveUsersId.setOnClickListener {
            val index = intent.getIntExtra("index", 0)
            var deleted = intent.getIntExtra("deleted", 0)
            users.removeAt(index)
            deleted++
            val intent = Intent(this@UpdateUserActivity, MainActivity::class.java)
            intent.putExtra("deleted", deleted)
            intent.putExtra("removeCheck", true)
            startActivity(intent)
        }
    }

    private fun btnUpdate() {
        with(binding) {
            btnUpdateUsersId.setOnClickListener {
                val index = intent.getIntExtra("index", 0)

                val newFirstName = etFirstNameId.text.toString().trim()
                val newLastName = etLastNameId.text.toString().trim()
                val newAge = etAgeId.text.toString().toIntOrNull()

                if (allInputsValid(
                        firstName = newFirstName,
                        lastName = newLastName,
                        age = newAge.toString()
                    )
                ) {
                    users[index] = users[index].copy(
                        firstName = newFirstName,
                        lastName = newLastName,
                        age = newAge
                    )
                    sendToMain(true)
                }
            }
        }
    }

    private fun editTextImutable() {
        with(binding) {
            val index = intent.getIntExtra("index", 0)

            etEmailId.apply {
                keyListener = null
                setText(users[index].email)
            }

            etFirstNameId.setText(users[index].firstName)
            etLastNameId.setText(users[index].lastName)
            etAgeId.setText(users[index].age.toString())
        }
    }

    private fun allInputsValid(firstName:String, lastName:String, age:String) : Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
            showSnackbar(binding.root , getString(R.string.fields))
            return false
        }
        return true
    }

    private fun sendToMain(succes : Boolean) {
        val intent = Intent(this@UpdateUserActivity, MainActivity::class.java)
        intent.putExtra("updateCheck", true)
        startActivity(intent)
    }

}