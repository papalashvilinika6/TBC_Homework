package com.example.myapplication

fun main() {
//    //for higher order functions
//    val sum = operate(5, 3) { a, b -> a + b }
//    val multiply = operate(5, 3) { a, b -> a * b }
//    println(sum)       // 8
//    println(multiply)  // 15
//
//    val helloGreeter = greeter("Hello")
//    println(helloGreeter("Nika"))  // Hello, Nika!
//
//    val double = { x: Int -> x * 2 }
//    println(double(5))  // 10  //lambda
//
//    val result1 = operate(2, 3, ::multiple)
//
//    val numbers1 = listOf(1, 2, 3, 4, 5)
//    val result = numbers1
//        .filter { it % 2 == 1 }   // [1,3,5]
//        .map { it * it }          // [1,9,25]
//        .sum()                    // 35
//    println(result)
//
//    val numbers = listOf(1, 2, 3, 4, 5)
//    val doubled = numbers.map { it * 2 }  // [2,4,6,8,10]
//    val even = numbers.filter { it % 2 == 0 }  // [2,4]
//    numbers.forEach { println(it) }
//
//
//    val numbers2 = listOf(1, 3, 5)
//    println(numbers2.none { it % 2 == 0 })
//
//    val allEven = numbers.all { it % 2 == 0 }
//    println(allEven)
//
//    val greaterThanTen = numbers.any { it > 10 }
//    println(greaterThanTen)

//
//    val numbers = listOf(1, 2, 3, 4, 5)
//
//// HOF: forEach takes a lambda as parameter
//    numbers.forEach { number ->
//        println(number * 2) // lambda used inside HOF
//    }

}

fun multiple(x: Int, y: Int) = x * y
fun operate(x: Int, y: Int, op: (Int, Int) -> Int): Int {
    return op(x, y)
}

fun greeter(prefix: String): (String) -> String {
    return { name -> "$prefix, $name!" }
}