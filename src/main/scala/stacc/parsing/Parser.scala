package stacc.parsing

import stacc.parsing.ast._

import scala.util.{Success, Try}
import fastparse.all._

object Parser {
 def parse(str: String): Try[Elem] = Success(Empty)




 val iset = P(ws ~ "{" ~ ws ~ prop ~ ws ~ "}")

 val prop = P(eqprop | memberProp)

 val eqprop = P("a")
 val memberProp = P("a")
 val id = P("a'")
 val disjunction = P("|")

 val memberOf = P(":")

 val union = P("u")

 val ws = P(" ").rep

}
