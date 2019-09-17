/** Implicit rules:
  * - Marking rule: Only definitions marked ''implicit'' are available.
  * -  Scope rule: An inserted implicit conversion must be in scope as
  *   a ''single identifier'', or be associated with the source or target
  *   type of the conversion.
  * - One-at-a-time rule: Only one implicit is inserted.
  * - Explicit-first rule: Whenever code type checks as it is written, no
  *   implicits are attempted.
  *
  * =Scoping=
  * The compiler will also look for implicit definitions in the companion
  * object of the source or expected target types of the conversion.
  *
  * =Where implicits are tried=
  * There are three places implicits are used:
  * - conversions to an expected type: use one type in a context where a
  *   different type is expected.
  * - conversions of the receiver of a selection: adapt the receiver of a
  *   method call (the object on which a method is invoked), if the method
  *   is not applicable on the original type.
  * - implicit parameters.
  */
object implicit_conversions extends App {
  implicit def intToString(x: Int): String = x.toString

  class Euro
  class Dollar
  object Dollar {
    /** The conversion is associated to the type [[Dollar]].
      *
      * The compiler will find such associated conversion every
      * time it needs to convert from an instance of type [[Dollar]].
      */
    implicit def dollarToEuro(x: Dollar): Euro = new Euro
  }

  /**
    * This will generate:
    * {{{
    *   implicit def InOp[T](v: T) = new InOp(v)
    * }}}.
    *
    * The usage of [[AnyVal]] will avoid allocating new object
    * and use static method for conversion instead.
    */
  implicit class InOp[T](val v: T) extends AnyVal {
    def in(s: Set[T]): Boolean =
      s contains v
  }
}
