package com.example.myapplication

import android.app.Activity
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
                val id = etIdNumberId.text.toString().trim()
                val firstName = etFirstNameId.text.toString().trim()
                val lastName = etLastNameId.text.toString().trim()
                val birthday = etBirthdayId.text.toString().trim()
                val address = etAdressId.text.toString().trim()
                val email = etEmailId.text.toString().trim()

                if (!allInputsValid(
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                        birthday = birthday,
                        address = address,
                        email = email)) return@setOnClickListener

                if (!emailIsValid(email = email)) {
                    showSnackbar(binding.root, getString(R.string.enter_real_email))
                    return@setOnClickListener
                }

                if (users.none { it.email == email }) {
                    val user = User(id, firstName, lastName, birthday, address, email)
                    sendToMain(user)
                }

            }
        }
    }

    private fun allInputsValid(id: String, firstName:String, lastName:String, birthday: String, address:String, email:String) : Boolean {
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || address.isEmpty() || email.isEmpty()) {
            showSnackbar(binding.root , getString(R.string.fields))
            return false
        }
        return true
    }

    private fun sendToMain(user: User) {
        val intent = Intent(this@AddUserActivity, MainActivity::class.java)
        intent.putExtra(User.KEY, user)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
