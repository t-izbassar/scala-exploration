class Prompt(val pref: String)

object Greeter {
  def greet(name: String)(implicit prompt: Prompt): Unit = {
    println(prompt.pref)
  }
}

implicit val p: Prompt = new Prompt(">")

/** In order to use implicit parameter the implicit definition
  * of the value must be in scope.
  */
Greeter.greet("asd")

def maxListOrdering[T](l: List[T])(ordering: Ordering[T]): Option[T] =
  l match {
    case Nil => None
    case List(x) => Some(x)
    case x :: xs =>
      maxListOrdering(xs)(ordering) match {
        case None => Some(x)
        case s@Some(m) => if (ordering.gt(x, m)) Some(x)
        else s
      }
  }

println(maxListOrdering(List(3, 7, 10, 0))(Ordering.Int))

/** For the implicit parameters it is best to use very specific type as
  * this will make it easier for compiler to provide correct instance.
  */
def maxListImpOrdering[T](l: List[T])(implicit ordering: Ordering[T]): Option[T] =
  l match {
    case Nil => None
    case List(x) => Some(x)
    case x :: xs =>
      maxListOrdering(xs)(ordering) match {
        case None => Some(x)
        case s@Some(m) => if (ordering.gt(x, m)) Some(x)
        else s
      }
  }

println(maxListImpOrdering(List(3, 7, -5, 0)))

def maxList[T](l: List[T])(implicit ordering: Ordering[T]): Option[T] =
  l match {
    case Nil => None
    case List(x) => Some(x)
    case x :: xs =>
      // The ordering is propagated to the further implicit call
      maxList(xs) match {
        case None => Some(x)
        case s@Some(m) => if (ordering.gt(x, m)) Some(x)
        else s
      }
  }

println(maxList(List(3, 7, 9, 0)))

def maxListWithImplicitly[T](l: List[T])(implicit ordering: Ordering[T]): Option[T] =
  l match {
    case Nil => None
    case List(x) => Some(x)
    case x :: xs =>
      // The ordering is propagated to the further implicit call
      maxListWithImplicitly(xs) match {
        case None => Some(x)
          // The ordering instance is asked implicitly (without the name)
        case s@Some(m) => if (implicitly[Ordering[T]].gt(x, m)) Some(x)
        else s
      }
  }

println(maxListWithImplicitly(List(3, 7, 12, 0)))

/** The Context Bound `T : Ordering` is asking for an implicit
  * instance of `Ordering[T]` to be available in scope. The
  * instance is then used with [[implicitly]] call.
  *
  * This is much more flexible than `T <: Ordering[T]` as context
  * bound do not require the type `T` to change. It only needs
  * implicit definition of the correct type in scope.
  */
def maxListWithContextBound[T : Ordering](l: List[T]): Option[T] =
  l match {
    case Nil => None
    case List(x) => Some(x)
    case x :: xs =>
      // The ordering is propagated to the further implicit call
      maxListWithContextBound(xs) match {
        case None => Some(x)
        // The ordering instance is asked implicitly (without the name)
        case s@Some(m) => if (implicitly[Ordering[T]].gt(x, m)) Some(x)
        else s
      }
  }

println(maxListWithContextBound(List(3, 7, 11, 0)))
