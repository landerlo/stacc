package stacc.logic

import scalaz.{INil, Semigroup, \/, \/-, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._
import scalaz.Semigroup._
import scalaz.syntax.semigroup._
import scalaz.syntax.monad._
import stacc.ast.PSet.ConcPSet
import stacc.logic.Ev._
import stacc.math.domain

object Unification {

  def unify(resolve: (Ref \/ PSet) => Ev[Prop[PSet]])(ps: NEL[Prop[Ref \/ PSet]]): Ev[Prop[PSet]] = {
    ps.tail.foldLeft[Ev[Prop[PSet]]](domain(ps.head).flatMap(resolve)) {
      case (ev, b) =>
        for {
          domB <- domain(b)
          rb <- resolve(domB)
          rev <- ev
          unified <- unifyProps(resolve)(rev, rb)
        } yield unified
    }
  }

  def unifyProps(resolve: (Ref \/ PSet) => Ev[Prop[PSet]])(a: Prop[PSet], b: Prop[PSet]): Ev[Prop[PSet]] = (a, b) match {
    case (MemberOf(as: ConcPSet), MemberOf(bs: ConcPSet)) => Congruent(as, bs).eval(resolve).map(p => ee(p))
    case (Equals(as: PSet), Equals(bs: PSet)) => Equal(as, bs).eval.map(p => :=(p))
    case (_, _) => ???
  }


}

