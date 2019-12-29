package com.rockthejvm

//1. Chapter
//Extending App means that everything inside the curly brackets is executable
object Basics extends App {

  //defining a value - is similar to a constant in java (final) --> The value cannot be changed once it has been assigned
  val meaningOfLife: Int = 42 // final int meaningOfLife = 42

  //Regular Data types in scala: Int, Boolean, Char, Double, Float, String
  //In scala you don't always need to specify the type - the compiler is smart enough
  val aBoolean = false // Type mentioning is optional here.

  //Strings and String operations
  val aString = "I love Scala"
  //Strings can be composed (concatenated) with the "+" Operator
  val aComposedString = "I" + " " + "love" + " " + "Scala"
  //With the "$" sign you can inject other values if you mark the string with s"
  val anInterpolatedString = s"The meaning of life is $meaningOfLife"

  //expressions --> Are structures that can be reduced to a value
  //In Java etc. you think in instructions - Do this, do that, increment that value, loop until the boolean is true etc.
  //In Scala we think in terms of values and composing these values to obtain new values
  val anExpression = 2 + 3
  //Even the if-statement is an expression
  //if-expression:
  val ifExpression = if (meaningOfLife > 43) 56 else 999 //in other languages: meaningOfLife > 43 ? 56 : 999
  //In scala it's more readable and can be chained since it doesn't use this ternary operator
  val chainedIfExpression =
    if (meaningOfLife > 43) 56
    else if (meaningOfLife < 0) -2
    else if (meaningOfLife > 999) 78
    else 0

  //code blocks
  //Is defined by curly brackets. Inside are definitions - local values, functions, classes, inner code blocks or whatever. But at the end you have to return a value.
  //The last expression of the code block is the value of the code block and is being returned
  val aCodeBlock = {
    //definitions
    val aLocalValue = 67

    //value of block is the value of the last expressions
    aLocalValue + 3
  }

  //Functions are besides values another fundamental concept in scala since scala is a functional programming language
  //define a function with the Keyword def. After the "=" sign has to follow a single expression!
  def myFunction(x: Int, y: String): String = y + " " + x

  //Most of the time a single line is not enough so code Blocks are used for functions (This is possible because code Blocks are also expressions)
  def myFunction2(x: Int, y: String) = {
    y + " " + x
  }

  //recursive functions
  //How does this work?:
  /*
    factorial(5) = 5 * factorial(4)       = 5 * 24  = 120 --> Final result
    factorial(4) = 4 * factorial(3)       = 4 * 6   = 24
    factorial(3) = 3 * factorial(2)       = 3 * 2   = 6
    factorial(2) = 2 * factorial(1)       = 2 * 1   = 2
    factorial(1) = 1                      = 1
   */

  //In Scala we don't use loops or iteration, we use RECURSION!
  //Iteration is heavily discouraged in scala - Think in functions and recursion!
  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else n * factorial(n - 1)
  }
  println(factorial(5))

  //The Unit return type = no meaningful value === "void" in other languages
  //Unit type is a type of SIDE EFFECTS --> printing something on screen, showing something on screen, sending something to socket/server, storing something in a file
  println("I love Scala")  //System.out.println() --> return void in Java, in Scala Unit.

  def myUnitReturningFunction(): Unit = {
    println("I don't love returning Unit")
  }
  //This is the same value returned by functions that return Unit!
  val theUnit = ()

}