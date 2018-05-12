package stacc.ast

import org.scalatest.FreeSpec
import PSet._
import AstSyntax._
import scalaz.{Monad, \/-, NonEmptyList => NEL}
import Project.project
import stacc.logic.{Bottom, Ev, Top}
import stacc.logic.Ev._
import stacc.logic.Ev
import scalaz.syntax.monad._


class ProjectSpec extends FreeSpec {

  "Project a variable from a PSet" - {
    "Variable exists" in {
      assert { project(PSet('a := ∅, 'b := ∅), 'b) === Top(NEL( := (∅) )) }
    }
    "Variable exists with multiple properties" in {
      assert { project(PSet('b ee 'A, 'a := ∅, 'b := ∅), 'b) === Top(NEL( ee('A), :=(∅) )) }
    }

    "Variable doesn't exist" in {
      val ps = PSet('a := 'A, 'b := ∅, 'E:= ∅)
      assert { project(ps, 'z) === Bottom('z ee(ps)) }

      val x: Ev[NEL[Equals]] = implicitly[Monad[Ev]].point( NEL(:=(∅) ))

      val p = for {
        a <- project(ps, 'a)
        na = a.head.leftSide.toString.take(1)

        b <- project(ps, Var(na.toString))
      } yield b
      println(p)
    }

  }

}
