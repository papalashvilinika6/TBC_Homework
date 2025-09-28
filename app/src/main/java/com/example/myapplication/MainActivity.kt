package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText = findViewById<AppCompatEditText>(R.id.edit_Text)
        val button = findViewById<AppCompatButton>(R.id.btnCalculate)
        val result = findViewById<AppCompatTextView>(R.id.resultText)
        val toggleLanguage = findViewById<AppCompatToggleButton>(R.id.tglLanguage)

        toggleLanguage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editText.hint = getString(R.string.hint1_eng)
                button.text = getString(R.string.calculateBtn_eng)
                result.text = getString(R.string.result_eng)
            } else {
                editText.hint = getString(R.string.hint1)
                button.text = getString(R.string.calculateBtn)
                result.text = getString(R.string.result)
            }
        }


        button.setOnClickListener {
            if(toggleLanguage.isChecked) {
                val text = editText.text
                val numberText = text.toString()

                if(numberText.isNotEmpty() && numberText.length <= 4) {
                    val number = numberText.toInt()
                    result.text = calculateEng(number)
                }else {
                    result.text = "Invalid Number!"
                }
            } else {
                val text = editText.text
                val numberText = text.toString()

                if(numberText.isNotEmpty() && numberText.length <= 4) {
                    val number = numberText.toInt()
                    result.text = calculateGeo(number)
                }else {
                    result.text = "არასწორი რიცხვი!"
                }
            }

        }


    }

    fun calculateGeo(number : Int) : String? {
        val ones : Map<Int, String> = mapOf(
            1 to "ერთი", 2 to "ორი", 3 to "სამი", 4 to "ოთხი", 5 to "ხუთი",
            6 to "ექვსი", 7 to "შვიდი", 8 to "რვა", 9 to "ცხრა",
            11 to "თერთმეტი", 12 to "თორმეტი", 13 to "ცამეტი",
            14 to "თოთხმეტი", 15 to "თხუთმეტი", 16 to "თექვსმეტი",
            17 to "ჩვიდმეტი", 18 to "თვრამეტი", 19 to "ცხრამეტი"
        )

        val tens : Map<Int, String> = mapOf(
            1 to "ათი", 2 to "ოცდა", 3 to "ოცდა",
            4 to "ორმოცდა", 5 to "ორმოცა", 610 to "სამოცდა",
            7 to "სამოცდა", 8 to "ოთხმოცდა", 9 to "ოთხმოცდა"
        )

        val hundreds : Map<Int, String> = mapOf(
            1 to "ას", 2 to "ორას", 3 to "სამას", 4 to "ოთხას", 5 to "ხუთას",
            6 to "ექვსას", 7 to "შვიდას", 8 to "რვაას", 9 to "ცხრაას", 10 to "ათასი"
        )

        return when {
            number == 0 -> "ნული"
            number == 1000 -> hundreds[10]
            number == 10 -> tens[1]
            number < 20 -> ones[number]
            number < 100 -> {
                when {
                    number%10 == 0 -> {
                        return if((number/10) % 2 == 0) tens[number/10]?.replace("და","ი")
                        else tens[number/10] + "ათი"
                    }
                    (number/10) % 2 == 0 -> return tens[number/10] + ones[number%10]
                    else -> return tens[number/10] + ones[(number%10) + 10]
                }
            }
            number < 1000 -> {
                if((number%100) == 0) hundreds[number/100] + "ი" else hundreds[number/100] + "${calculateGeo(number%100)}"
            }

            else -> {return "არასწორი რიცხვი!"}
        }

    }

    fun calculateEng(number : Int) : String? {
        val ones : Map<Int, String> = mapOf(
            1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five",
            6 to "six", 7 to "seven", 8 to "eight", 9 to "nine",
            11 to "eleven", 12 to "twelve", 13 to "thirteen",
            14 to "fourteen", 15 to "fifteen", 16 to "sixteen",
            17 to "seventeen", 18 to "eighteen", 19 to "nineteen"
        )

        val tens : Map<Int, String> = mapOf(
            1 to "ten", 2 to "twenty", 3 to "thirty", 4 to "forty", 5 to "fifty",
            6 to "sixty", 7 to "seventy", 8 to "eighty", 9 to "ninety"
        )

        val hundreds : Map<Int, String> = mapOf(
            1 to "one hundred", 2 to "two hundred", 3 to "three hundred",
            4 to "four hendred", 5 to "five hundred", 6 to "six hundred",
            7 to "seven hundred", 8 to "eight hundred", 9 to "nine hundred",
        )

        return when {
            number == 0 -> "Zero"
            number == 1000 -> "One Thousand"
            number == 10 -> "Ten"
            number < 20 -> ones[number]
            number < 100 -> if((number%10) == 0) tens[number/10] else tens[number/10] + " " + ones[number%10]
            number < 1000 -> hundreds[number/100] + " " + if((number%100) != 0) calculateEng(927%100) else ""
            else -> "Invalid Number!"
        }
    }


}