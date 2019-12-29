package com.rockthejvm

//2. Chapter
object ObjectOrientation extends App {

  //Scala is an object oriented language --> Therefore the following concepts are available:

  //class and instance
  class Animal {
    //define fields
    val age: Int = 0

    //define Methods
    def eat() = println("I'm eating")
  }

  val anAnimal = new Animal

  //inheritance
  //Cat will have age and eat
  class Cat extends Animal {

  }

  //inheritance with constructor arguments
  class Dog(name: String) extends Animal

  val aDog = new Dog("Lassie")

  //constructor arguments are NOT fields! --> aDog.name is not possible

  //However you can promote constructor arguments to a field by adding val before the constructor argument:
  class Lizard(val name: String) extends Animal

  val aLizard = new Lizard("Carl")
  aLizard.name

  //subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog("Hachi")
  aDeclaredAnimal.eat() //This will be called from the Dog class if the method has been overridden in the class Dog -> The most derived method will be called at runtime. Just like in Java

  //abstract class --> Not all fields and methods need to have implementations
  abstract class WalkingAnimal {
    val hasLegs = true

    //Method that doesn't provide an implementation. Whichever class extends WalkingAnimal will need to implement that Method.
    def walk(): Unit
  }

  //All fields and Methods are by default PUBLIC in Scala.
  //This can be restricted by writing private/ protected in front of the field/ Method

  //"interface" = ultimate abstract type
  trait Carnivore { //= Fleischfresser
    def eat(animal: Animal): Unit
  }

  //Scala offers single class inheritance and multi trait inheritance
  //That means a class can extend only one class but multiple traits
  //"single-class inheritance, multi-trait "mixing""
  class Crocodile extends Animal with Carnivore /* with SomeOtherTrait */ {
    //override Keyword to implement the Method from Carnivore
    override def eat(animal: Animal): Unit = println("I am eating you, animal!")

    //Optional: Override of the eat Method in Animal
    override def eat(): Unit = super.eat()
  }

  //Scala method notation and method naming is really good compared to other programming languages
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  //Methods with a single argument can be called in this "infix notation"
  aCroc eat aDog //object method argument

  trait Philosopher {
    def ?!(thought: String): Unit //valid method name
  }

  //Operators in scala are actually methods
  val basicMath = 1 + 2 //+ is actually a method belonging to the Int type
  val sameBasicMath = 1.+(2) //equivalent

  //anonymous classes
  //Abstract types (abstract class or trait) cannot be instantiated by themselves
  val dinosour = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")

    /*
  This is what you tell the compiler by doing an anonymous inner class
    class Carnivore_Anonymous_235324 extends Carnivore {
        override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
    }
    val dinosaur = new Carnivore_Anonymous_235324
   */
  }


  //singleton object
  //The object keyword defines a singleton object. This also creates the single instance of the type
  object MySingleton { //The only instance of the MySingleton type
    val mySpecialValue = 53278
    def mySpecialMethod(): Int = 325
    //There is a special Method in scala called "apply"
    //apply can take any arguments and is present anywhere
    def apply(x: Int): Int = x + 1
  }
  MySingleton.mySpecialMethod()
  //The presence of an apply Method in a class allows Instances of that class to call the apply method in the following way:
  MySingleton.apply(65)
  MySingleton(65) //equivalent
  //This is really useful for functional programming. The apply Method allows Instances to be invoked just like functions

  //In scala you can define a class and a singleton with the same name in the same file
  object Animal { //companion object
    //This is perfectly valid. Further up in this file we already defined "class Animal"
    //The class Animal and the object Animal are called COMPANIONS
    //Companions can access each other's private fields/methods!
    //But the singleton Animal and instances of Animal are different things.
    val canLiveIndefinetely = false
  }

  val animalsCanLiveForever = Animal.canLiveIndefinetely //"static" fields/methods

  /*
  case classes = lightweight data structures with some boilerplate
  When you create a case class the compiler automatically generates the following:
    - sensible equals and hash code
    - sensible and quick serialization (Because we often send these instances over the wire in distributed Applications, for example with Akka)
    - companion with apply
    - pattern matching
   */
  case class Person(name: String, age: Int)

  //We can omit the "new" Keyword because the Person case class also has a companion object with an apply Method taking a name and an age and returning a Person instance
  //--> case class instances can be constructed without new
  val bob = Person("Bob", 54) //Person.apply("Bob", 54)

  //exceptions
  try {
    //code that can throw
    val x: String = null
    x.length //Will normally crash the program. But here the exception get's catched
  } catch  { //catch (Exception e) { ... }
    case e: Exception => "some faulty error message"
  } finally {
    //Execute some code no matter what
  }

  //generics
  //Works for classes, abstract classes and traits
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  //Using a generic with a concrete type (here Int)
  val aList: List[Int] = List(1, 2, 3) //List.apply(1, 2, 3)
  val first = aList.head //returns Int
  val rest = aList.tail
  val aStringList = List("hello", "Scala")
  val firstString = aStringList.head //returns String


  //Point #1: In Scala we usually operate with IMMUTABLE values/objects
  //This means: Any modification to an instance of a class should always result in a new instance (Best practice!)
  /*
    Benefits:
      1) works miracles in multithreaded/ distributed environments
      2) helps making sense of the code ("reasoning about")
   */
  val reversedList = aList.reverse //returns a NEW list!

  //Point #2: Scala is the closest to the ObjectOriented ideal
  //All the code and all the values that we operate with are inside an instance of some type.
  //Every single piece of code is part of a class or part of a object.
  //No values and no methods outside a class/ object --> Therefore Scala is a true OO Language

  //When you define an object that extends "App" you are actually inheriting from the main Method in App
  //Methods on objects are similar to Java "static" Methods --> There is already a static main Method implemented by extending "App" which wraps the entire body

}