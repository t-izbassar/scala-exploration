object pre_initialization extends App {

  /** As traits don't have constructor the usual notion
    * of parameterizing works via abstract `val`s.
    */
  trait Rational {
    val numerator: Int
    val denominator: Int
  }

  /**
    * Creating anonymous class seems like invoking constructor:
    * `new Rational(n, d)`, but there is a difference. The `n` and `d`
    * values are being evaluated before class is initialized. So the
    * values of `n` and `d` are available for initialization of that class.
    *
    * For traits the situation is the opposite. The `n` and `d` are evaluated
    * as part of the initialization of the anonymous class, but the anonymous class
    * is initialized after the trait.
    */
  val r = new Rational {
    override val numerator: Int = 1
    override val denominator: Int = 2
  }

  trait RationalWithInitialization {
    val numerator: Int
    val denominator: Int
    require(denominator != 0)
  }

  try {
    /** The exception will be thrown because denominator will
      * have the default value of 0, when class will be initializing.
      *
      * This shows that Trait is initialized before anonymous class expressions
      * will take care of values.
      */
    new RationalWithInitialization {
      override val numerator: Int = 1
      override val denominator: Int = 2
    }
  } catch {
    // This prints that requirement failed.
    case ex: Throwable => println(ex.getLocalizedMessage)
  }

  /** With pre-initialized fields we can initialize field of a
    * subclass before the superclass is called.
    */
  val rwi = new {
    val numerator = 1
    val denominator = 2
  } with RationalWithInitialization

  /** Syntax of pre-initializing with extends. The pre-initialized fields
    * are initialized before the superclass constructor is called so their
    * initializers cannot refer to the object that's being constructed.
    * Pre-initialized fields behave in this respect like class constructor
    * arguments.
    */
  object oneHalf extends {
    val numerator = 1
    val denominator = 2
  } with RationalWithInitialization

  /** Similar syntax for classes. */
  class RationalClass(n: Int, d: Int) extends {
    val numerator = n
    val denominator = d
  } with RationalWithInitialization
}
