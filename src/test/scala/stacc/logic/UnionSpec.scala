package stacc.logic

import org.scalatest.FreeSpec
import scalaz.\/-
import stacc.ast.AstSyntax._
import stacc.ast.{Equals, PSet, Union}
import stacc.ast.PSet.empty
import stacc.resolution.Canon.canonical
class UnionSpec extends FreeSpec {

  val A = PSet('a := empty)
  val B = PSet('b := empty)

  "Simple unions" - {
    "Set of properties union syntax" in assert {
      (A u B) === Union(\/-(A), \/-(B))
    }
    "Canonical union of sets of properties" in assert {
      canonical(v => Top(Equals(v)))(A u B) === Top(PSet('a := empty, 'b := empty))
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
