package stacc.logic

import scalaz.{Functor, INil, Monad, Semigroup, \/, \/-, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._
import scalaz.Semigroup._
import scalaz.syntax.semigroup._
import scalaz.syntax.monad._
import stacc.ast.PSet.ConcPSet
import stacc.logic.Ev._
import stacc.math.domain

object Unification {
  import Prop.propMonad
  import Ev.EvMonad

  implicit val fprop = implicitly[Monad[Prop]]
  def unify(resolve: (Ref \/ PSet) => Ev[Prop[PSet]])(ps: NEL[Prop[Ref \/ PSet]]): Ev[Prop[PSet]] = {
    val z: Ev[Ref \/ PSet] = for {
      resolved: Prop[PSet] <- resolve(ps.head)
      dom <- domain(resolved)
    } yield dom

    ps.tail.foldLeft[Ev[Prop[PSet]]](z) {
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
    case (MemberOf(as: ConcPSet), MemberOf(bs: ConcPSet)) => Congruent(as, bs).eval(resolve).map(p => MemberOf(p))
    case (Equals(as: PSet), Equals(bs: PSet)) => Equal(as, bs).eval.map(p => Equals(p))
    case (_, _) => ???
  }


}

