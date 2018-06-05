package stacc.logic

import org.scalatest.FreeSpec
import scalaz.-\/
import stacc.ast.{PSet, Ref, Resolve, SET}
import stacc.ast.AstSyntax._

class FunctionSpec extends FreeSpec {

  "Functions" - {
    "first" in {
      val prod = PSet('T ee SET, 'first ee 'T, 'second ee 'T)

      println(Resolve.resolve(prod)(-\/(Ref("first"))))

    }
  }

}
