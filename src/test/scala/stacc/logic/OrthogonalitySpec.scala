package stacc.logic
import org.scalatest.FreeSpec
import stacc.ast.PSet
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._
import SubsumptionRelation._

class OrthogonalitySpec extends FreeSpec {

  "Orthogonal test" - {
    val A = PSet('a := empty)
    val Az = PSet('z := empty, 'a := empty)
    val B = PSet('b := empty)
    val XA = PSet('a := B)

//    "orthogonal" in assert { relation(A, B) === Orthogonal(A, B)
//    "Subsumed" in assert { relation(A, Az) === Subsumption(Az, A) }
//    "incompatible" in assert { relation(A, XA) === Incompatible(A, XA) }
  }
}
