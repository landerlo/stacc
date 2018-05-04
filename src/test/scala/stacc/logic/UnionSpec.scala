package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast.AstSyntax._
import stacc.ast.ConcreteVarPSet
import stacc.ast.ConcreteVarPSet.empty

class UnionSpec extends FreeSpec {

  val A = ConcreteVarPSet('a := empty)
  val B = ConcreteVarPSet('b := empty)

  "Simple unions" - {
    "Set of properties union" in pending
//      (A U B) === Union(A, B)


  }
  "Unification of different labels" in pending

}


/*

  a = {
  x e B
  t = a u { x }

}





 */
