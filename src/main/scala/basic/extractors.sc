/** Extractor object. When the compiler sees reference
  * to it it will call [[EMail.unapply]].
  */
object EMail extends ((String, String) => String) {

  /** Injection */
  def apply(user: String, domain: String) =
    s"$user@$domain"

  /** Extraction */
  def unapply(arg: String): Option[(String, String)] = {
    val parts = arg split "@"
    if (parts.length == 2)
      Some(parts(0), parts(1))
    else None
  }
}

EMail.unapply("John@mail.com")
EMail.unapply("John Doe")

val str = "John@mail.com"

str match {
  case EMail(user, domain) => println(s"$user at $domain")
}

object Twice {
  def apply(s: String): String = s + s
  def unapply(s: String): Option[String] = {
    val half = s.substring(0, s.length / 2)
    if (half == s.substring(s.length / 2)) Some(half)
    else None
  }
}

str match {
  case Twice(s) => println(s"$s is twice")
  case _ => println("Other")
}

object UpperCase {
  /** This extractor does not bind any variable. */
  def unapply(s: String): Boolean =
    s.toUpperCase == s
}

def userTwiceUpper(s: String) = s match {
  /** The ''x @ UpperCase()'' binds x to a pattern
    * matched by [[UpperCase]].
    *  */
  case EMail(Twice(x @ UpperCase()), domain) =>
    "match: " + x + " in domain " + domain
  case _ => "no match"
}

userTwiceUpper("JOHNJOHN@mail.com")
userTwiceUpper("asd")
val m = Map(1 -> "a", 2 -> "b", 3 -> "c")
m.map (a => {
  val (key, _) = a
  key
})
