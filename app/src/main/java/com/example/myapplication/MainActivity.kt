package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.text.isEmpty

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var users = mutableSetOf<User>()
    private var deleted = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        btnAdd()
        btnRemove()
        btnUpdate()

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
                    twStatusId.setText(R.string.success)
                    twStatusId.setTextColor(Color.GREEN)
                } else {
                    twStatusId.setText(R.string.fail)
                    twStatusId.setTextColor(Color.RED)
                }

                twActiveUsersId.text = getString(R.string.active, users.size)
            }
        }
    }

    private fun btnRemove() {
        with(binding) {

            btnRemoveUsersId.setOnClickListener {
                val email = etEmailId.text.toString().trim()
                if(email.isEmpty() || !emailIsValid(email = email)) {
                    showSnackbar(getString(R.string.email_to_remove))
                    return@setOnClickListener
                }

                users.firstOrNull { it.email == email }?.let { user ->
                    users.remove(user)
                    twStatusId.setText(R.string.successDelete)
                    twStatusId.setTextColor(Color.GREEN)
                    deleted++
                } ?:  twStatusId.setText(R.string.userNot)
                      twStatusId.setTextColor(Color.RED)


                twDeletedUsersId.text = getString(R.string.deleted, deleted)
                twActiveUsersId.text = getString(R.string.active, users.size)
            }
        }
    }

    private fun btnUpdate() {
        with(binding)  {

            btnUpdateUsersid.setOnClickListener {
                val email = etEmailId.text.toString().trim()
                if(email.isEmpty() || !emailIsValid(email = email)) {
                    showSnackbar(getString(R.string.email_to_update))
                    return@setOnClickListener
                }

                val firstName = etFirstNameId.text.toString().trim()
                val lastName = etLastNameId.text.toString().trim()
                val age = etAgeId.text.toString().trim()

                var user = users.firstOrNull() { it.email == email }
                if(user == null) {
                    twStatusId.text = getString(R.string.email_not_found)
                    twStatusId.setTextColor(Color.RED)
                }else {
                    if (allInputsValid(firstName = firstName, lastName = lastName, age = age, email = email)) return@setOnClickListener

                    users.remove(user)
                    user = User(firstName, lastName, age.toIntOrNull(), email)
                    users.add(user)
                    twStatusId.text = getString(R.string.updated_email)
                    twStatusId.setTextColor(Color.GREEN)
                }
            }
        }
    }


    private fun emailIsValid(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun allInputsValid(firstName:String, lastName:String, age:String, email:String) : Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()) {
            showSnackbar(getString(R.string.fields))
            return false
        }
        return true
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }


}