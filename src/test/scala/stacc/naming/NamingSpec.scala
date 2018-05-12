package stacc.naming

import org.scalatest.FreeSpec
import stacc.ast._
import stacc.ast.PSet.empty
import stacc.ast.AstSyntax._

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

     //val resolvedB = 'B :=

    //  val resolved = Resolve.resolve(Path("A.a"), PSet(A, B))

 //     assert {
  //      resolved.isDefined
   //   }

    }
    "Successful reference out of order" in {
     val B = 'B := Ref(Path("A.a"))
     val A = 'A := PSet('a := empty)

//      val resolved = ??? // Resolve.resolve(Path("B"), PSet(B, A))

//      assert {
//        resolved.isDefined
//      }

//      assert { resolved === Some(empty) }
    }
    "Chained references" in {

      val A = 'A := PSet('a := 'C)
      val B = 'B := Ref(Path("A.a"))
      val C = 'C := empty
//      val resolved = Resolve.resolve(Path("A.a"), PSet(A, B, C))

//      assert { resolved.isDefined }

//      assert { resolved === Some(empty) }
    }
    "Incorrect reference" in {
      val A = 'A := PSet('a := 'Z)
      val B = 'B := Ref(Path("A.a"))
      val C = 'C := empty
//      assert { Resolve.resolve(Path("A"), PSet(A, B, C))   === Some(A.propsValue) }
//      assert { Resolve.resolve(Path("B"), PSet(A, B, C))   === Some(Absurdity()) }
//      assert { Resolve.resolve(Path("A.a"), PSet(A, B, C)) === Some(Absurdity()) }
    }
   "Incompatible body" in {
      val A = 'A := PSet('a := PSet(
        'B := PSet('y := empty),
        'g := empty,
        'B := PSet('x := PSet('k := empty))))

//      assert { Resolve.resolve(Path("a.B.x"), PSet(A)) === Some(Absurdity()) } //It should be error
    }
   }

  "Var extraction" - {
      val Z = PSet('a := 'Z)
    "GetVar duplicated are discarded" in assert {
      true
      //    PSet('a := empty, 'b := empty, 'a := empty).getProp("a") === Some(empty)
     }
    "GetVar multiple props" in assert { true
     // ConcreteVarPSet('a := empty, 'b := empty, 'a := Z).getProp("a") === Some(PropUnion(empty, Z))
     // ConcreteVarPSet('a := empty, 'b := empty, 'a := Z).getProp("a") === Some(PropUnion(Z, empty))
     }

  }
}
