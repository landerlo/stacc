package stacc.logic
import org.scalatest.FreeSpec
import scalaz._
import scalaz.syntax.monad._
import stacc.ast.Prop._
import stacc.ast.AstSyntax._
import stacc.ast.PSet._

class PropMonadSpec extends FreeSpec {

  "Prop functor" in {
    assert { (('a := empty).map(x => x)) === ('a := empty) }

  }
}
