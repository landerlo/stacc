package stacc.resolution

import org.scalatest.FreeSpec
import stacc.ast._
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._
import stacc.logic.{Bottom, Top}
import Resolve.resolve
import scalaz.{-\/, \/, \/-, NonEmptyList => NEL}
import AstSyntax._
import stacc.math.domain
import scalaz.syntax.monad._
import stacc.logic._


class NamingSpec extends FreeSpec {

  "Resolving references in graph" - {
    /* {
        A = { a = {} }
        B = A.a
       }
     */
    "Successful reference resolving" in {
      val A = 'A := PSet('a := empty)

      val B = 'B := Ref(Path("A.a"))


      assert { resolve(PSet(A, B))(-\/(Ref("A.a"))) === Top(:=(empty)) }

    }
    "Successful reference out of order" in assert {
      val B = 'B := Ref(Path("A.a"))
      val A = 'A := PSet('a := empty)

      resolve(PSet(B, A))(-\/(Ref("B"))) === Top(:=(Ref("A.a")))
    }

    "Chained references" in {

      val A = 'A := PSet('a := 'C)
      val B = 'B := Ref(Path("A"))
      val C = 'C := empty

      assert { resolve(PSet(A, B, C))(-\/(Ref("B.a"))) === Top(:=(Ref("C"))) }

    }

    "Incorrect reference" in {
      val A = PSet('a := 'Z)
      val B = Ref(Path("A.a"))
      val C = empty

      assert { resolve(PSet('A := A, 'B := B, 'C := C))(-\/(Ref("A.b"))) === Bottom[Ref \/ PSet](Lie('b ee A)) }
    }

   "Incongruent, incompatible variables" in {
      val E1 = PSet('x := PSet('k := empty))
      val E2 = PSet('y := empty)

      val EsNotConguent = PSet(
        'a := PSet(
          'E := E1,
          'g := empty,
          'E := E2
        )
      )
     assert { resolve(EsNotConguent)(-\/(Ref("a.E.x"))) === Bottom(Lie(Equal(E1, E2))) }
    }
   }

  "Var projection" - {
    import Project.project
    val Z = PSet('a := 'Z)

    "GetVar duplicates are removed" in assert {
       project('a)(PSet('a := empty, 'b := empty, 'a := empty)) === Top(NEL(:=(empty)))
     }

    "GetVar multiple props" in assert {
      project('a)(PSet('a := empty, 'b := empty, 'a := Z)) === Top(NEL(:=(empty), :=(Z)))
     }
  }
}
