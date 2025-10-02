package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usersInfo = mutableMapOf<String, String>()

        addButton(usersInfo)
        getButton(usersInfo)
    }


    fun getButton(usersInfo : MutableMap <String, String>) {
        binding.getUserBtnId.setOnClickListener {
            for ((key, value) in usersInfo) {
                if(key == binding.inputEmailId.text.toString()) {
                    binding.resultTextId.text = "Email: $key"
                    binding.resultText2Id.text = "Username $value"
                } else {
                    binding.resultTextId.text = "Account Cannot Found"
                    binding.resultText2Id.text = ""
                }
            }

        }
    }

    fun addButton(usersInfo: MutableMap<String, String>) {
        binding.addUserBtnId.setOnClickListener {
            val email = binding.inputEmailId.text.toString()
            val name = binding.inputNameId.text.toString()

            if('@' in email && name.isNotEmpty()){
                if(email in usersInfo.keys) {
                    binding.inputEmailId.error = "This Email Already Registered"
                }else {
                    usersInfo[email] = name
                    binding.usersNumTextId.text = binding.usersNumTextId.text?.dropLast(1)
                    binding.usersNumTextId.append(usersInfo.size.toString())
                }
            }else {
                if(!email.contains('@')) binding.inputEmailId.error = "Enter Correct Email"
                if(name.isEmpty()) binding.inputNameId.error = "Enter Name To Add"
            }

        }
    }

}