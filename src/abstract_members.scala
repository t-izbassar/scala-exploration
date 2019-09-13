object abstract_members extends App {

  trait Abstract {
    // Abstract type
    type T
    def transform(t: T): T
    // Abstract value
    val initial: T
    // Abstract variable
    var current: T
  }

  class Concrete extends Abstract {
    type T = Int
    def transform(t: Int): Int = t + 5
    val initial: Int = 0
    var current: Int = 9
  }

}
