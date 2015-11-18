import scalaz._
import std.option._
import scala.util.{ Try, Success, Failure }

object Main extends App {

  def division(x: Double): Option[Double] = if (x != 0) Some(100 / x) else None

  def logarithme(x: Double): Option[Double] = if (x > 0) Some(Math.log(x)) else None

  // composition with flatMap
  def composition(x: Double): Option[Double] = division(x) flatMap logarithme

  // composition with for-Comprehension
  def composition2(x: Double): Option[Double] = for {
    y <- division(x)
    z <- logarithme(y)
  } yield z

  // Kleisli wrapping                                                  
  val divisionK = Kleisli { division }
  val logarithmeK = Kleisli { logarithme }

  //*********** Kleisli compositions ****************
  
  val compositionK = logarithmeK <=< divisionK
  
  val compositionK2 = logarithmeK compose divisionK

  val compositionK3 = divisionK >=> logarithmeK

  val compositionK4 = divisionK andThen logarithmeK

  val compositionK5 = divisionK >==> logarithme

  val compositionK6 = logarithmeK <==< division
  
  //*************************************************
  
  //******* executing composition ******************* 
  println("compositionK(0): " +compositionK(0))
  println("compositionK(-10): "+compositionK(-10))
  println("compositionK(100): "+compositionK(100))
  println("compositionK(2): "+compositionK(2))

  // read a value from console
  def lireConsole(u: Unit): Try[Double] = Try(Console.readLine("Entrer une valeur :").toDouble)

  // Kleisli wrapping for lireConsole
  val lireConsoleK = Kleisli { lireConsole }

  // transformation function
  def transform[T](t: Try[T]): Option[T] = t match {
    case Success(e) => Some(e)
    case Failure(e) => None
  }

  // second composition for reading value from console
  val deuxiemeCompositionK = compositionK <=< (lireConsoleK mapK transform)

  // executing compositions for comparing results ****
  // enter 2 
  println("deuxiemeCompositionK(): "+deuxiemeCompositionK())
  println("compositionK(2): "+compositionK(2))
}