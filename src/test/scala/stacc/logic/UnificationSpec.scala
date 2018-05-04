package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast.ConcreteVarPSet
import stacc.ast.ConcreteVarPSet.empty
import stacc.ast.AstSyntax._
import Unification.unify
class UnificationSpec extends FreeSpec {

  val A = ConcreteVarPSet('a := empty)
  val B = ConcreteVarPSet('b := empty)

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
