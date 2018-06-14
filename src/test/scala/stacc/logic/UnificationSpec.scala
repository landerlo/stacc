package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast._
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._
import Unification.unify
import scalaz.{\/, \/-, NonEmptyList => NEL}
import Resolve.resolvePath

class UnificationSpec extends FreeSpec {
  val A =  PSet('a := empty)
  val B =  PSet('b := empty)
  val C =  PSet('c := empty)

  "Orthogonal set membership" - {
    "membership of orthogonal sets results in membership of the union" in assert {
      unify(_ => ???)(NEL.nels(ee(A), ee(B))) === Top(ee(PSet('a := empty, 'b := empty)))
    }

  }
  "Unification of incongruent variable" in {
    val e1X = PSet('k := empty)
    val e2X = empty
    assert { unify(_ => ???)(NEL.nels(ee(PSet('x := e1X)), ee(PSet('x := e2X)))) === Bottom(Lie(Equal(e1X, e2X))) }
  }

  "Unification with nested incongruency" in {
    val incongruent = PSet {
      'b := PSet(
        'x := empty,
        'y := empty,
        'x := PSet('z := empty)
      )
    }
    val lie = Bottom(Lie(Equal(empty, PSet('z := empty))))
    assert { resolvePath(Path("b.x"))(incongruent)(\/-(incongruent)) === lie }
  }

}

/*r

  a = {
  x e B
  t = a u { x }

}





 */
