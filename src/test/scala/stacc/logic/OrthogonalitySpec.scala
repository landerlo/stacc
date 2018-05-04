package stacc.logic
import org.scalatest.FreeSpec
import stacc.ast.ConcreteVarPSet
import stacc.ast.ConcreteVarPSet.empty
import stacc.ast.AstSyntax._
import SubsumptionRelation._

class OrthogonalitySpec extends FreeSpec {

  "Orthogonal test" - {
    val A = ConcreteVarPSet('a := empty)
    val Az = ConcreteVarPSet('z := empty, 'a := empty)
    val B = ConcreteVarPSet('b := empty)

    "orthogonal" in assert { relation(A, B) === Orthogonal(A, B) }
    "Subsumed" in assert { relation(A, Az) === Subsumption(Az, A) }
  }
}
