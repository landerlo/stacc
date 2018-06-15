package stacc.logic

import org.scalatest.FreeSpec
import scalaz.{-\/, \/-}
import stacc.ast._
import stacc.ast.AstSyntax._
import AMember.A_SET

class FunctionSpec extends FreeSpec {

  "Functions" - {
    val tup = PSet('T ee SET, 'first ee 'T, 'second ee 'T)
    "first" in {
      assert { Resolve.resolve(tup)(Ref("first")) === Top(AMember(A_SET)) }
    }

    "target" in {
      val f = PSet('T ee SET, 'first ee 'T, 'second ee 'T, 'target := 'first)

      assert { Resolve.resolve(f)(Ref("target")) === Top(AMember(A_SET)) }

      val app = f u PSet()
    }
    "T" in {
      val f = PSet('T ee SET, 'first ee 'T, 'second ee 'T, 'target := 'first)

      assert { Resolve.resolve(f)(Ref("T")) === Top(AMember(SET)) }

      val app = f u PSet()
    }  }

}
