//package stacc.peano
//
//import org.scalatest.FreeSpec
//import stacc.logic.{Bottom, Top}
//import stacc.ast.AstSyntax._
//import stacc.ast.PSet
//import PSet.empty
//
//class PeanoTest extends FreeSpec {
//
//  "Nat = { pred: Nat | {} } is correct" in {
//    val succ = 'Succ := PSet('pred ee 'Nat)
//    val nat = 'Nat := ('Succ :|: empty)
//
//    val peano = ISet(succ, nat)
//    assert { eval(peano) === Top(peano) }
//  }
//
//  "Absurdity" - {
//    "member of the empty set" in {
//      assert { eval( 'a ee Empty) === Bottom('a ee Empty) }
//    }
//
//    "non termination is allowed -TBD" - {
//      "Self referetial Nat = { pred: Nat | {} } is correct" in {
//        val natTerminating = 'Nat := ISet('pred ee 'Nat)
//
//        assert {
//          eval(natTerminating) === Top(natTerminating)
//        }
//      }
//    }
//  }
//
//    "Typing" - {
//      val A = 'A := ISet('a := Empty)
//
//      "well typed" in {
//        val wellTyped = ISet(A, 'B := ISet('a := Empty))
//        assert( eval(wellTyped) === Top(wellTyped))
//      }
//      "wrongly typed" in {
//        val wronglyTyped = ISet(A, 'B := ISet('c := Empty))
//        assert( eval(wronglyTyped) === Bottom(wronglyTyped))
//      }
//
//
//    }
//
//
//}
