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
    "membership of orthogonal sets results in membership of the union" in assert {
      unify(p => Top(Equals(p)))(NEL.nels(ee(A), ee(B))) === Top(ee(PSet('a := empty, 'b := empty)))
    }

  }
  "Unification of incongruent variable" in {
    val e1X = PSet('k := empty)
    val e2X = empty
    assert { unify(p => Top(Equals(p)))(NEL.nels(ee(PSet('x := e1X)), ee(PSet('x := e2X)))) === Bottom(Lie(Equal(e1X, e2X))) }
  }
}

/*r

  a = {
  x e B
  t = a u { x }

}





 */
