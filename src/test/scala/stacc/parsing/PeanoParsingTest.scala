package stacc.parsing

import org.scalatest.FreeSpec
import stacc.parsing.Parser.parse
import stacc.parsing.ast._

import scala.util.Success

class PeanoParsingTest extends FreeSpec {

  "Peano Numbers" - {

    "Nat definition" in {
      val peanoDef = "Nat = { pred: Nat | {} }"

      assert{ parse(peanoDef) === Success(Eq(Id("Nat"), Disj(Ref("Nat"), Empty))) }
    }

    "Nat examples" in {
      //assert { parse(" one = Nat u { pred = {} }") === Success(Eq(Id("one"), Union(SetRef(Id("Nat")), LitSet(Seq(Eq(Id("Pred"), Empty)))))) }
    }
  }

}
