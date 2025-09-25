package com.example.myapplication
import kotlin.random.Random


class Task1 {

    fun run() {
        print("Enter X:")
        val x = readLine() ?: "0"

        print("Enter Y:")
        val y = readLine() ?: "0"

        var operationType : Char? = null
        while(operationType == null) {
            print("Enter Operation Type(/,*,%,!):")
            val realOperationalType = readln()

            val c = realOperationalType[0]
            if(realOperationalType.length == 1) {
                if (c == '!' || c == '*' || c == '/' || c == '%') {
                    operationType = c
                } else {
                    println("Invalid!")
                }
            } else {
                println("Invalid!")
            }
        }

        val newX = convertToInt(x)
        val newY = convertToInt(y)


        when(operationType) {
            '*' -> println("$newX X $newY = ${newX*newY}")
            '/' -> if(newY != 0) println("$newX / $newY = ${newX/newY}") else println("Can't Divide!")
            '%' -> if(newY != 0) println("$newX % $newY = ${newX%newY}") else println("Can't Divide!")
            '!' -> { val result = factorial((newX / newY))
                     println("Factorial of ($newX/$newY) = $result")
                   }

        }

        println("Do you want to restart? <Y?N> (Default: No)")
        val answer = readln()
        if((answer[0] == 'y' || answer[0] == 'Y') && answer.length == 1) {
            run()
        }
    }

     private fun convertToInt(a : String) : Int {
        var number = 0
        if(a.all {x -> !x.isDigit()}){
            number = Random.nextInt(257) - 127
        }else {
            for (i in a) {
                if (i.isDigit()) {
                    number *= 10
                    number += i.digitToInt()
                }
            }
        }
        return number
    }

    private fun factorial(a: Int): Long {
        if(a <= 1) return 1
        var result = 1L
        for(i in 2..a) result *= i
        return result
    }



}