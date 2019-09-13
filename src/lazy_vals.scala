import scala.annotation.tailrec

object lazy_vals extends App {
  /** The lazy expression will be evaluated
    * when the val is used the first time.
    */
  trait LazyRational {
    val numerator: Int
    val denominator: Int

    /** Defer calculation. */
    lazy val simplifiedNumerator: Int = numerator / g
    lazy val simplifiedDenominator: Int = denominator / g

    override def toString: String = s"$simplifiedNumerator/$simplifiedDenominator"

    /** This won't be executed, before numerator and
      * denominator have been initialized.
      */
    private lazy val g = {
      require(denominator != 0)
      gcd(numerator, denominator)
    }

    @tailrec
    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)
  }

  val lazyRational = new LazyRational {
    override val numerator: Int = 4
    override val denominator: Int = 6
  }

  // This will print 2/3
  println(lazyRational)
}
