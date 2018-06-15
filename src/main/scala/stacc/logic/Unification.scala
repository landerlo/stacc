package stacc.logic

import scalaz.{Functor, INil, Monad, Semigroup, \/, \/-, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._
import scalaz.Semigroup._
import scalaz.syntax.semigroup._
import scalaz.syntax.functor._
import scalaz.syntax.monad._
import stacc.ast.PSet.ConcPSet
import stacc.logic.Ev._
import stacc.math.domain
import scalaz.{Equal => ZEqual, _}

object Unification {

  implicit val fprop: Functor[Prop] = implicitly[Functor[Prop]]
  import Prop.propMonad
  import scalaz.syntax.monad._
  def unify(resolve: (Ref \/ PSet) => Ev[Prop[PSet]])(ps: NEL[Prop[PSet]]): Ev[Prop[PSet]] = {

    ps.tail.foldLeft[Ev[Prop[PSet]]](Top(ps.head)) {
      case (ev, b) => ev.flatMap(a => unifyProps(resolve)(a, b))
    }
  }

  def unifyProps(resolve: (Ref \/ PSet) => Ev[Prop[PSet]])(a: Prop[PSet], b: Prop[PSet]): Ev[Prop[PSet]] = (a, b) match {
    case (MemberOf(as: ConcPSet), MemberOf(bs: ConcPSet)) => Congruent(as, bs).eval(resolve).map(p => MemberOf(p))
    case (Equals(as: PSet), Equals(bs: PSet)) => Equal(as, bs).eval.map(p => Equals(p))
    case (a, b) => println(s"$a $b"); ???
  }


}

