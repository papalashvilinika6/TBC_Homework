package com.example.myapplication

class Math {
    fun usg (a:Int, b:Int):Int {
        if(b == 0) return a;
        return usg(a = b,b = a%b)
    }

    fun usj (a:Int, b:Int):Int {
        return (a*b)/usg(a,b)
    }

    fun containsSymbol(a:String):Boolean {
        if('$' in a) return true
        return false
    }

    fun countToHundred(n : Int = 98):Int {
        if (n == 0) return 0

        return n + countToHundred(n = n - 2)
    }

    fun reverseNumber(n : Int) : Int {
        var result = 0
        var number = n

        while(number > 0) {
            result = result*10 + number%10
            number/=10
        }

        return result
    }

    fun isPalindrome(a : String): Boolean {
        val size = a.length   //Using for loop
        val s = a.lowercase()

        for(i in 0 until size/2)  {
            if(s[i] != s[size - 1 -i]) return false
        }
        return true

        //val s = a.lowercase()   //Using built-in fun
        //return a == a.reversed()
    }


}