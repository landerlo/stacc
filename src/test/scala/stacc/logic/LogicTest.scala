package stacc.logic


import org.scalatest.FreeSpec
import stacc.ast.AstSyntax._
import stacc.ast.{PSet, Path, Resolve, ConcreteVarPSet}
import stacc.ast.ConcreteVarPSet.empty

class LogicTest extends FreeSpec {

    def isWellTyped(x: PSet): Boolean = {
      true
    }

    "Typing" - {
      val A = 'A := ConcreteVarPSet('a := empty)

      "well typed" in {
        val wellTyped = ConcreteVarPSet(A, 'B := ConcreteVarPSet('a := empty, 'b := 'A))
        assert { Resolve.resolve(Path("B"), wellTyped).map(isWellTyped).contains(true) }
      }
      "wrongly typed" in {
        val wronglyTyped = ConcreteVarPSet(A, 'B := ConcreteVarPSet('c := empty), 'B := ConcreteVarPSet('c := 'A))
        assert { Resolve.resolve(Path("B"), wronglyTyped).map(isWellTyped).contains(false) }
      }


    }


}
