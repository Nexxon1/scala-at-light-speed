import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

//5. Chapter
//Advanced Scala Features
object Advanced extends App {

  /**
    lazy evaluation - An expression is not evaluated until it's first used
   */
  lazy val aLazyValue = 2 //The number 2 is not associated to aLazyValue until it is used for the first time
  lazy val lazyValueWithSideEffect = {
    //Nothing will be actually printed to the console.
    println("I am so very lazy!")
    43
  }
  val valueWithSideEffect = {
    //This will be shown on the console because the code block was evaluated
    println("Value with side effect code block")
    43
  }
  val eagerValue = lazyValueWithSideEffect + 1 //Because you are using lazyValueWithSideEffect for the first time the code block will now be evaluated and the console line is being printed
  //Lazy evaluation is useful in infinite collections


  /**
    pseudo-collections (to be exact they are an own Type and not a collection)
   */
  //"pseudo-collections": Option, Try
  //They are very useful in unsafe methods
  def methodWhichCanReturnNull(): String = "Hello, Scala"
  if (methodWhichCanReturnNull() == null) {
    //defensive code against null
  }
  //This can be avoided with the Option Type
  val anOption = Option(methodWhichCanReturnNull()) //Some("Hello, Scala")
  //Option = "collection" which contains at most one element: Some(value) or None
  //None is a singleton Object and is the equivalent to null

  val stringProcessing = anOption match {
    case Some(str) => s"I have obtained a valid String $str"
    case None => "I obtained nothing"
  }
  //You can also operate on Options with map, flatMap and filter

  //"Try" guards against methods that can throw Exceptions
  def unsafeMethodWhichCanThrowException(): String = throw new RuntimeException
  //Normally:
  try {
    unsafeMethodWhichCanThrowException()
  } catch {
    case e: Exception => "defend against this evil exception"
  }
  //Scala uses the "Try" so called pseudo collection instead:
  //This is a Try Object containing either a String if the method went correctly (because that's the methods return type) or it can contain the Exception that was thrown
  val aTry = Try(unsafeMethodWhichCanThrowException())
  //aTry = "collection" with either a value if the code went well or an exception if the code threw an Exception
  val anotherStringProcessing = aTry match {
    case Success(validValue) => s"I have obtained a valid String: $validValue"
    case Failure(exception) => s"I have obtained an exception: $exception"
  }
  //map, flatMap and filter can also be used on Try


  /**
    * Evaluate something on another thread
    * (async programming)
    * This is also done with a pseudo collection called Future
    */
  //The following has to be imported: import scala.concurrent.ExecutionContext.Implicits.global
  //The imported global value is the equivalent of a Thread Pool - A Collection of Threads on which we can schedule an evaluation (e.g. see the example below)
  val aFuture = Future /* ( */ { //Code block in the constructor of Future   Future.apply()
    //This expression will be evaluated on another Thread
    println("Loading...")
    Thread.sleep(1000) //Blocks the running thread by 1 sec
    println("I have computed a value.")
    67
    //If we run this file we see the last thing that is printed on the console is "Loading...". (Note: You have to comment out Thread.sleep(1500) on the Main Thread so the Main Thread finishes before this async task)
    //The main Thread of the application finished before reaching "I have computed a value." and evaluating to 67
    //This is prove, that the Future Block is evaluated on another Thread
  }/* ) */    //These brackets are not needed

  println(aFuture)
  //If we do this under the main JVM Thread there is enough time to evaluate the Future before the program has finished
  Thread.sleep(1500)
  println(aFuture)

  //Future is a "collection" which contains a value when it's evaluated
  //Future is composable with map, flatMap and filter

  //Future, Try and Option are called monads in functional programming
  //monads are really abstract and hard to explain. Most of the time it is enough to think of Future, Try and Option of a sort of Collection

  /**
    * Implicits basics
    * Implicits are one of the most powerful features of the Scala compiler
    */
  //Use case #1: implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt = 41
  //Note how you don't have to pass the argument to the method.
  //This works because the parameter is implicit and the compiler tries to find an implicit value of type Int that can be injected as parameter
  //If there is no implicit value of the needed type or several implicit values of that type an Error is thrown by the compiler
  println(aMethodWithImplicitArgs) //aMethodWithImplicitArgs(myImplicitInt)

  //Use case #2: implicit conversions
  //Usually we do implicit conversions to add methods to existing types over which we don't have any control over the code
  implicit class MyRichInterger(n: Int) {
    //We wrapped an Integer n into the class MyRichInteger
    //The class has a method isEven that returns true if the wrapped Integer is even
    def isEven() = n % 2 == 0
  }
  //It is possible to call the method isEven() from an Integer even thought the method does not belong to the Int class.
  //The compiler notices that this code will not usually compile and searches an implicit wrapper
  println(23.isEven())  //Compiler does: new MyRichInteger(23).isEven()
  //These features makes Scala an incredibly expressive language
  //However implicits should be used carefully

}