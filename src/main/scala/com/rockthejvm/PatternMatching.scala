package com.rockthejvm

//4. Chapter
object PatternMatching extends App {

  //switch expression
  val anInteger = 55
  //Pattern match is an EXPRESSION -> because of that we can assign it to a value
  val order = anInteger match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => anInteger + "th" //Default case
  }
  println(order)

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 43) //Person.apply("Bob", 43)

  //Pattern matching is available mostly for case classes. For normal classes it is more difficult.
  val personGreeting = bob match  {
    //Here we are matching against a whole structure
    //If Bob conforms to the structure Person with name and age do something
    case Person(n, age) => s"Hi, my name is $n and i am $age years old."
    case _ => "Something else"
  }
  println(personGreeting)

  //Pattern matching can also deconstruct other data structures
  //Deconstructing tuples
  val aTuple = ("Eminem", "Rap")
  val artistDescription = aTuple match {
    //If this tuple conforms to a two member tuple structure then let artist and genre be the members of the tuple so we can use them for the interpolated String
    case (artist, genre) => s"$artist belongs to the genre $genre"
    case _ => "I don't know what you're talking about"
  }

  //Decomposing lists
  val aList = List(1, 2, 3)
  val listDescription = aList match {
    //If the List has the structure something, 2, something
    case List(_, 2, _) => "List containing 2 on its second position"
    //If the PM (Pattern Matching) doesn't match anything, it will throw a MatchError --> Always define a "_" case
    case _ => "List doesn't have 2 on the second position or contains more or less than 3 elements"
  }
  println(listDescription)
  //PM will try all cases in sequence --> If you write the wildcard case "_" at the beginning it will always execute that

}
