package stacc.logic


import org.scalatest.FreeSpec
import stacc.ast.AstSyntax._
import stacc.ast.{PSet, Path, Resolve}
import stacc.ast.PSet.empty

class LogicTest extends FreeSpec {

    def isWellTyped(x: PSet): Boolean = {
      true
    }

    "Typing" - {
      val A = 'A := PSet('a := empty)

      "well typed" in {
        val wellTyped = PSet(A, 'B := PSet('a := empty, 'b := 'A))
    //    assert { Resolve.resolve(Path("B"), wellTyped).map(isWellTyped).contains(true) }
      }
      "wrongly typed" in {
        val wronglyTyped = PSet(A, 'B := PSet('c := empty), 'B := PSet('c := 'A))
      //  assert { Resolve.resolve(Path("B"), wronglyTyped).map(isWellTyped).contains(false) }
      }


    }


}
