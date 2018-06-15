package stacc.ast

import org.scalatest.FreeSpec
import PSet._
import AstSyntax._
import stacc.logic.Lie
//import scalaz.{IList, Monad, \/-, NonEmptyList => NEL}
import Project.project
import stacc.logic.{Bottom, Ev, Top}
import stacc.logic.Ev._
import stacc.logic.Ev
import scalaz.{NonEmptyList => NEL, _}, Scalaz._

class ProjectSpec extends FreeSpec {

  "Project a variable from a PSet" - {
    "Variable exists" in {
      assert { project('b)(PSet('a := Ø, 'b := Ø)) === Top(NEL(Ø)) }
    }
    "Variable exists with multiple properties" in {
      assert { project('b)(PSet('b ee 'A, 'a := Ø, 'b := Ø)) === Top(NEL( ee('A), Ø)) }
    }

    "Variable doesn't exist" in {
      val ps = PSet('a := 'A, 'b := Ø, 'E := Ø)
      assert {
        project('z)(ps) === Bottom(Lie('z ee (ps)))
      }

      val x = implicitly[Monad[Ev]].point(NEL(Ø))

      val z: Ev[IList[String]] = IList("a", "b", "E").point[Ev]
      val xx: IList[Ev[Int]] = IList(Top(1), Top(2))
      val p = for {
        segs <- z
        res <- segs.map(s => project(Var(s))(ps)).sequence
      } yield res
      println(IList("a").initOption)

    //  println ((Top(IList("a", "b", "E")): Ev[IList[String]]).flatMap(segs => segs.map(s => project(ps, Var(s))).sequence))
    }
  }


}
