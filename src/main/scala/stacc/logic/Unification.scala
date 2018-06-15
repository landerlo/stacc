package stacc.logic

import scalaz.{Functor, INil, Monad, Semigroup, \/, \/-, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._
import scalaz.Semigroup._
import scalaz.syntax.semigroup._
import scalaz.syntax.functor._
import scalaz.syntax.monad._
import stacc.logic.Ev._
import stacc.math.domain
import scalaz.{Equal => ZEqual, _}

object Unification {

  import scalaz.syntax.monad._
  def unify(resolve: (PSet) => Ev[PSet])(ps: NEL[PSet]): Ev[PSet] = {

    ps.tail.foldLeft[Ev[PSet]](Top(ps.head)) {
      case (ev, b) => ev.flatMap(a => unifyProps(resolve)(a, b))
    }
  }

  def unifyProps(resolve: PSet => Ev[PSet])(a: PSet, b: PSet): Ev[PSet] = {
    println(s"Unifying $a $b")
    (a, b) match {
      case (AMember(ofA: ConcPSet), AMember(ofB: ConcPSet)) =>
        Congruent(ofA, ofB).eval(resolve).map(p => AMember(p))
      case (as: ConcPSet, bs: ConcPSet) =>
        Equal(as, bs).eval
      case (a, b) =>
        println(s"$a $b"); ???
    }
  }


}

