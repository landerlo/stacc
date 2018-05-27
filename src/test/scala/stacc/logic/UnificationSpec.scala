package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast._
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._
import Unification.unify
import scalaz.{\/, \/-, NonEmptyList => NEL}

class UnificationSpec extends FreeSpec {
  val A =  PSet('a := empty)
  val B =  PSet('b := empty)
  val C =  PSet('c := empty)

  "Orthogonal set membership" - {
    "membership of orthogonal sets results in union" in assert {
      unify(p => Top(Equals(p)))(NEL.nels(ee(A), ee(B))) === Top(ee(PSet('a := empty, 'b := empty, 'c := empty)))
    }

  }
  "Unification of incongruent variable" in {
    val E1 = PSet('x := PSet('k := empty))
    val E2 = PSet('y := empty)
    assert { unify(p => Top(Equals(p)))(NEL.nels(ee(E1), ee(E2))) === Bottom(Lie(Congruent(E1, E2))) }
  }
}

/*r

  a = {
  x e B
  t = a u { x }

}





 */
