package stacc.peano

import org.scalatest.FreeSpec
import stacc.logic.{Bottom, Lie, Top}
import stacc.ast.AstSyntax._
import stacc.ast._
import PSet.empty
import Resolve.resolve
import stacc.ast.AstSyntax._

class PeanoTest extends FreeSpec {

  "Nat = { pred: Nat | {} } is correct" in {
    val succ = 'Succ := PSet('pred ee 'Nat)
    val nat = 'Nat := ('Succ :|: empty)

    val peano = PSet(succ, nat)
    assert { resolve(peano)(Ref("Nat")) === Top(nat) }
  }

  "Absurdity" - {
    "member of the empty set" in {
      assert { resolve(PSet('a ee empty))(Ref("a")) === Bottom(Lie.apply('e ee empty)) }
    }

    "non termination is allowed -TBD" - {
      "Self referetial Nat = { pred: Nat | {} } is correct" in {
        val natTerminating = 'Nat := PSet('pred ee 'Nat)

        assert {
          resolve(PSet(natTerminating))(PSet(natTerminating)) === Top(natTerminating)
        }
      }
    }
  }

    "Typing" - {
      val A = 'A := PSet('a := empty)

      "well typed" in {
        val wellTyped = PSet(A, 'B := PSet('a := empty))
        assert( resolve(wellTyped)(wellTyped) === Top(wellTyped))
      }
      "wrongly typed" in {
        val otherA = 'A := PSet('c := empty)
        val wronglyTyped = PSet(A, otherA)
        assert { resolve(wronglyTyped)(wronglyTyped) === Bottom(Lie(Congruent(ConcPSet(Set(A)), ConcPSet(Set(otherA))))) }
      }


    }


}
