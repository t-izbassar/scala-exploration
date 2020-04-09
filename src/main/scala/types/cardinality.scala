package types

object cardinality {

  /**
   * Two is a sum-type which is isomorphic to `Boolean`.
   */
  sealed trait Two
  case object Up extends Two
  case object Down extends Two

  /**
   * Isomorphism between types.
   */
  def twoToBoolean(two: Two): Boolean = two match {
    case Up => true
    case Down => false
  }
  def booleanToTwo(boolean: Boolean): Two =
    if (boolean) Up
    else Down

  /** Cardinality of `Option[A]` is 1 + cardinality of A. */

  /** a * 1 = a */
  def aMultiplyOneToA[A](tuple: (A, Unit)): A = tuple._1
  def aToTuple[A](a: A): (A, Unit) = (a, ())

  def absurd[T](nothing: Nothing): T = nothing

  /** `a + 0 = a` */
  def aEitherNothingToA[A](either: Either[A, Nothing]): A =
    either match {
      case Left(value) => value
      case Right(value) => absurd(value)
    }
  def aToEither[A](a: A): Either[A, Nothing] = Left(a)

  /** `a -> b` has cardinality `b^a`. For each a we can peek every b. */

  /** `a^b * a^c = a^(b + c)` */
  def tupleToFunction[A, B, C](tuple: (B => A, C => A)): Either[B, C] => A = {
    case Left(b) => tuple._1(b)
    case Right(c) => tuple._2(c)
  }
  def functionToTuple[A, B, C](fun: Either[B, C] => A): (B => A, C => A) =
    (b => fun(Left(b)), c => fun(Right(c)))

  /** `(a * b)^c = a^c * b^c` */
  def funToTuple[A, B, C](fun: C => (A, B)): (C => A, C => B) =
    (c => fun(c)._1, c => fun(c)._2)
  def tupleToFun[A, B, C](tuple: (C => A, C => B)): C => (A, B) =
    c => (tuple._1(c), tuple._2(c))

  /** `(a^b)^c = a^(b * c)` ~ Carrying. */
  def twoFunToOne[A, B, C](twoFun: C => B => A): (B, C) => A =
    (b, c) => twoFun(c)(b)
  def oneFunToTwo[A, B, C](oneFun: (B, C) => A): C => B => A =
    c => b => oneFun(b, c)
}
