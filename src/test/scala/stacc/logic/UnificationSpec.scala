package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast.PSet
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._
import Unification.unify
class UnificationSpec extends FreeSpec {

  val A = PSet('a := empty)
  val B = PSet('b := empty)

  "Simple unions" - {
    "Empty unifies to empty" in assert { unify(Seq()) === Seq() }
    "orthogonal sets unchanged" in {
      unify(Seq(A,B)) === Seq(A, B)
    }

  }
  "Unification of different labels" in pending

}


/*

  a = {
  x e B
  t = a u { x }

}





 */
