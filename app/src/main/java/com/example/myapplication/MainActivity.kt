package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fields = listOf(
            binding.emailId,
            binding.userNameId,
            binding.firstNameId,
            binding.lastNameId,
            binding.ageId
        )

        clearButton(fields)
        saveButton(fields)

    }

    private fun clearButton(list: List<EditText?>) {
        binding.clearBtnId?.setOnLongClickListener {
            for(i in list) i?.text?.clear()
            true
        }
    }

    private fun errorMessage(list: List<EditText?>) : Boolean {
        val isEmpty = list.any { it?.text?.isEmpty() == true }
        binding.resultTextId?.text = ""
        var isError = false

        if (isEmpty) {
            binding.resultTextId?.text = getString(R.string.emptyMsg)
            isError = true
        }

        if (binding.emailId?.text?.contains('@') == false) {
            binding.emailId?.error = "Please enter real mail"
            isError = true
        }

        if ((binding.userNameId?.text?.length ?: 0) < 10) {
            binding.userNameId?.error = "Please enter more than 10 characters"
            isError = true
        }

        if (binding.firstNameId?.text?.isEmpty() == true) {
            binding.firstNameId?.error = "Please enter first name"
            isError = true
        }

        if (binding.lastNameId?.text?.isEmpty() == true) {
            binding.lastNameId?.error = "Please enter last name"
            isError = true
        }

        if (binding.ageId?.text?.isEmpty() == true) {
            binding.ageId?.error = "Please enter Age"
            isError = true
        }
        return isError
    }

    private fun saveButton(list: List<EditText?>) {
        binding.saveBtnId?.setOnClickListener {

            if(!errorMessage(list)) {
                val username = binding.userNameId?.text.toString()
                val email = binding.emailId?.text.toString()
                val name = binding.firstNameId?.text.toString() + " " + binding.lastNameId?.text.toString()
                val age : Int? = binding.ageId?.text.toString().toIntOrNull()

                val intent = Intent(this, InfoPageActivity::class.java).apply {
                    putExtra("username",username)
                    putExtra("email",email)
                    putExtra("name",name)
                    putExtra("age",age)
                }
                startActivity(intent)
            }

        }

    }

}