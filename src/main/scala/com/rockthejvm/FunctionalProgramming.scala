package com.rockthejvm

//3. Chapter
object FunctionalProgramming extends App {

  //Scala is an OO Language
  class Person(name: String) {
    //If you define an apply Method to a Class
    def apply(age: Int) = println(s"I have aged $age years")
  }

  val bob = new Person("Bob")
  bob.apply(43)
  bob(43) //INVOKING bob as a function === bob.apply(43)
  //The presence of an apply method allows an instance of a class to be invoked like a function

  /*
    Scala runs on the JVM
    The JVM was not fundamentally build for functional programming but for OO programming.
    So how do we implement functional programming on the JVM? -
    Functional programming:
      - compose functions
      - pass functions as args
      - return functions as results
    Conclusion: FunctionX = Function1, Function2, Function3, ..., Function22 (22 is the max. number of args you can pass to a function)
   */

  //Function that takes an Int and returns an Int. This is just a plain trait (Interface) with an apply method
  val simpleIncrementer = new Function1[Int, Int] {
    //Function one because we pass 1 arg to the function, the second Int is the return type
    override def apply(arg: Int): Int = arg + 1
  }
  simpleIncrementer.apply(23) //returns 24
  simpleIncrementer(23) //same as simpleIncrementer.apply(23)
  //we basically defined a function!
  //ALL SCALA FUNCTIONS THAT WE DEFINE (with def myFunc...) ARE INSTANCES OF THESE FUNCTION_X TYPES

  //Function with 2 arguments and a String return type
  val stringConcatenator = new Function2[String, String, String] {    //Syntactical sugar: new ((String, String) => String) {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }
  stringConcatenator("I love", " Scala") //"I love Scala"

  //syntactical sugar that will remove the boiler plate:
  val doubler: (Int) => Int = (x: Int) => 2 * x
  doubler(4) //8
  /*
    With boilerplate
    val doubler: Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(x: Int) = 2 * x
    }
   */
  //Even shorter:
  val doubler2 = (x: Int) => 2 * x
  doubler2(4) //8


  //The functions that take functions as args or return functions as results are called
  //higher-order functions --> Take function as args/ return functions as results (or both)
  //map is a special method on the List type that allows the passing of a function. It expects a function from Int to some other Type
  val aMappedList = List(1,2,3).map(x => x + 1) //Map is a higher order function
  println(aMappedList) //List(2, 3, 4)
  //For every element in the List applying the function will return another List. So we will get the List(1, 2), List(2, 4), List(3, 6) and concats the Lists together
  val aFlatMappedList = List(1, 2, 3).flatMap(x => List(x, 2 * x))
  println(aFlatMappedList)  //List(1, 2, 2, 4, 3, 6)
  //Alternative syntax:
  val aFlatMappedList2 = List(1, 2, 3).flatMap {
    x => List(x, 2 * x)
  }
  //.filter takes a function from Int to Boolean. The returned List will ony contain the values where the predicates returns true
  val aFilteredList = List(1, 2, 3, 4, 5).filter(x => x < 4)  //even shorter: filter(_ <4)
  println(aFilteredList) //List(1, 2, 3)
  //BTW because we usually only work with immutable objects in scala the .map, .flatMap, .filter Method will always return another instance of a List. Because of that we can chain these Methods
  //e.g. create all pairs between the numbers 1, 2, 3 and the letters 'a', 'b', 'c'
  val allPairs = List(1, 2, 3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))
  println(allPairs) //List(1-a, 1-b, 1-c, 2-a, 2-b, 2-c, 3-a, 3-b, 3-c)
  //We are iterating through collections without looping or iterations, just with map, flatMap and filter

  //for keyword = for comprehension (not for loop!)
  var alternativePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter" //equivalent to the map/flatMap chain above. Identical to the compiler!
  println(alternativePairs) //List(1-a, 1-b, 1-c, 2-a, 2-b, 2-c, 3-a, 3-b, 3-c)

  /**
    * Collection types
    * (map, flatMap, filter works for all collection types)
    */
  //lists
  //List would be Java's LinkedList
  val aList = List(1, 2, 3, 4, 5)
  val firstElement = aList.head
  val rest = aList.tail
  val aPrependedList = 0 :: aList //The "::" Operator prepends the element to the List --> List(0, 1, 2, 3, 4, 5)
  val anExtendedList = 0 +: aList :+ 6  //"+=" prepends an Element to a List. ":+" appends an element to a List --> List(0, 1, 2, 3, 4, 5, 6)

  //sequences (Seq). Sequence is a trait (Interface) that has a companion object which has an apply method
  //Seq would be Java's List
  val aSequence: Seq[Int] = Seq(1, 2, 3)  //Seq.apply(1, 2, 3)
  //With Sequences you can access an element at a given Index   ??? also seems to work with List
  val accessedElement = aSequence(1) //aSequence.apply(1). The element at index 1: 2

  //vectors - very fast for large data (fast Seq implementation)
  val aVector = Vector(1, 2, 3, 4, 5)

  //sets = no duplicates
  val aSet = Set(1, 2, 3, 4, 1, 2, 3) //Set(1, 2, 3, 4)
  val setHas5 = aSet.contains(5) //false
  //You can add elements to a set with the "+" and remove elements with "-"
  val anAddedSet = aSet + 5 //Set(1, 2, 3, 4, 5) - But not necessarily in this order because the order is not important in a Set collection.
  val aRemovedSet = aSet - 3 //Set(1, 2, 4)

  //Ranges - useful for iteration (by using flatMap and map)
  val aRange = 1 to 1000 //This collection does not contain all numbers between 1 and 1000 but it can be processed as if it did
  val twoByTwo = aRange.map(x => 2 * x).toList // List(2, 4, 6, 8, ..., 2000)

  //Tuples - groups of values under the same value
  val aTuple = ("Bon Jovi", "Rock", 1982) //All these informations are grouped in the value aTuple
  println(aTuple._1)

  //Maps - Key value pairs
  val aPhonebook: Map[String, Int] = Map(
    ("Daniel", 124124),
    "Marian" -> 231423 //("Marian", 231423)
  )
  println(aPhonebook.get("Marian"))

}