package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast.AstSyntax._
import stacc.ast.PSet
import stacc.ast.PSet.empty

class UnionSpec extends FreeSpec {

  val A = PSet('a := empty)
  val B = PSet('b := empty)

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
