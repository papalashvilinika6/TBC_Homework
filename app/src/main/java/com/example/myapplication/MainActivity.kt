package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener


class MainActivity : AppCompatActivity() {

    val users = mutableListOf<User>(
        User(id = "1", firstName = "გრიშა", lastName = "ონიანი", birthday = "1724647601641", address = "სტალინის სახლმუზეუმი", email = "grisha@mail.ru"),
        User(id = "2", firstName = "Jemal", lastName = "Kakauridze", birthday = "1714647601641", address = "თბილისი, ლილოს მიტოვებული ქარხანა", email = "jemal@gmail.com"),
        User(id = "3", firstName = "Omger", lastName = "Kakauridze", birthday = "1724647701641", address = "თბილისი, ასათიანი 18", email = "omger@gmail.com"),
        User(id = "32", firstName = "ბორის", lastName = "გარუჩავა", birthday = "1714947701641", address = "თბილისი, იაშვილი 14", email = ""),
        User(id = "34", firstName = "აბთო", lastName = "სიხარულიძე", birthday = "1711947701641", address = "ფოთი", email = "tebzi@gmail.com")
    )


    private lateinit var binding: ActivityMainBinding

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

        searchUser()
        addToList()
        addBtn()

    }

    private fun searchUser() {
        binding.etSeatchFieldId.addTextChangedListener { it ->
            val input = it.toString()
            if (input.isEmpty()) {
                binding.twTextResultId.text = ""
                binding.btnAddUsersId.visibility = View.GONE
                return@addTextChangedListener
            }

            val user = users.firstOrNull() {
                it.firstName.contains(input, true) ||
                        it.lastName.contains(input, true) ||
                        it.email.contains(input, true) ||
                        it.address.contains(input, true) ||
                        it.birthday.contains(input, true)
            }

            if (user != null) {
                val userText = getString(
                    R.string.user_info,
                    user.id,
                    user.firstName,
                    user.lastName,
                    user.birthday,
                    user.address,
                    user.email
                )

                binding.twTextResultId.text = userText
                binding.btnAddUsersId.visibility = View.GONE
            } else {
                binding.twTextResultId.text = getString(R.string.notFound)
                binding.btnAddUsersId.visibility = View.VISIBLE
                addBtn()
            }
        }


    }

    private fun addBtn() {
        binding.btnAddUsersId.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addToList() {
        val getUserResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val user = result.data?.getParcelableExtra<User>(User.KEY)
                user?.let {
                    users.add(it)
                    binding.twTextResultId.text = "${it.firstName} ${it.lastName}"
                }
            }
        }
    }

}

