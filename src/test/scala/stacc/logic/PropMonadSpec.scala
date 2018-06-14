package stacc.logic
import org.scalatest.FreeSpec
import scalaz._
import scalaz.syntax.monad._
import stacc.ast.Prop._
import stacc.ast.AstSyntax._
import stacc.ast.{Equals, PSet, Prop}
import stacc.ast.PSet._

class PropMonadSpec extends FreeSpec {

  "Prop functor" in {
    val p: Prop[PSet] = Equals(empty)
    assert { p.map(x => x) === Equals(empty) }
  }
  "Prop monad" in {
    val p: Prop[PSet] = Equals(empty)
    assert { p.flatMap(x => Equals(x)) === Equals(empty) }
  }
}
