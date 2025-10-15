package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Data.users
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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

        count()
        displayStatus()
        btnAdd()
        btnUpdate()

    }

    private fun btnAdd() {
        binding.btnAddUsersId.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun btnUpdate(){
        binding.btnUpdateUsersId.setOnClickListener {
            if(users.isNotEmpty()) {
                val randomIndex = Random.nextInt(users.size)
                val intent = Intent(this, UpdateUserActivity::class.java)
                intent.putExtra("random", randomIndex)
                intent.putExtra("deleted", deleted)
                startActivity(intent)
            }else {
                binding.twStatusId.text = getString(R.string.is_empty)
            }
        }
    }


    private fun displayStatus() {
        with(binding) {
            val checkAdd = intent.getBooleanExtra("check", false)
            val checkRemove = intent.getBooleanExtra("removeCheck", false)
            val checkUpdate = intent.getBooleanExtra("updateCheck", false)

            when {
                checkAdd -> {
                    twStatusId.setText(R.string.success)
                    twStatusId.setTextColor(Color.GREEN)
                }

                !checkAdd -> {
                    twStatusId.setText(R.string.fail)
                    twStatusId.setTextColor(Color.RED)
                }

                checkRemove -> {
                    twStatusId.setText(R.string.successDelete)
                    twStatusId.setTextColor(Color.GREEN)
                }

                checkUpdate -> {
                    twStatusId.setText(R.string.updated_email)
                    twStatusId.setTextColor(Color.GREEN)
                }

                else -> {
                    twStatusId.setText(R.string.status)
                    twStatusId.setTextColor(Color.WHITE)
                }
            }
        }
    }

    private fun count() {
        val newDelete = intent.getIntExtra("deleted", 0)
        deleted = newDelete
        binding.twActiveUsersId.text = getString(R.string.active, users.size)
        binding.twDeletedUsersId.text = getString(R.string.deleted, newDelete)
    }

}