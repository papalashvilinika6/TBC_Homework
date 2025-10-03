package com.example.myapplication

import android.os.Bundle
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val unorderedAnagrams = mutableListOf<String>()

        saveButton(unorderedAnagrams)
        outputButton(unorderedAnagrams)
        clearButton(unorderedAnagrams)

    }


    private fun saveButton(mutableList: MutableList<String>) {
        binding.saveBtnId.setOnClickListener {
            val input = binding.inputAnagramId.text.toString().trim()
            val result =

            mutableList.add(input)
            binding.resultTextId.append(input)
            binding.resultTextId.append(", ")
            binding.inputAnagramId.text?.clear()
        }
    }

    private fun group(unorderedAnagrams: MutableList<String>): String {
        return unorderedAnagrams
            .groupBy { i -> i.toCharArray().sorted().joinToString("") }
            .values
            .joinToString("\n") { group ->
                group.joinToString(", ")
            }
    }

    private fun outputButton(unorderedAnagrams: MutableList<String>) {
        binding.outputBtnId.setOnClickListener {
            binding.resultTextId.text = ""
            binding.resultTextId.text = group(unorderedAnagrams)
            binding.resultCountId.text = binding.resultCountId.text.dropLast(1)
            binding.resultCountId.append(count(unorderedAnagrams).toString())
        }
    }

    private fun count(unorderedAnagrams: MutableList<String>): Int {
        val groups = unorderedAnagrams
            .groupBy { it.toCharArray().sorted().joinToString("") }
            .values
        return groups.size
    }

    private fun clearButton(unorderedAnagrams: MutableList<String>) {
        binding.clearButtonId.setOnClickListener {
            unorderedAnagrams.clear()
            binding.resultTextId.text = ""
            binding.resultCountId.text = "Collections Number: 0"
        }
    }


}