package stacc.logic

import org.scalatest.FreeSpec
import stacc.ast.PSet
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._
import Unification.unify
import scalaz.{NonEmptyList => NEL}

class UnificationSpec extends FreeSpec {

  val A =  PSet('a := empty)
  val B =  PSet('b := empty)
  val C =  PSet('c := empty)

  "Orthogonal set membership" - {
    "membership of orthogonal sets results in union" in {
      unify(NEL.nels(ee(A), ee(B))) === Top(ee(PSet('a := empty, 'b := empty, 'c := empty)))
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
