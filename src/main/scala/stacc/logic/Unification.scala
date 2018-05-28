package stacc.logic

import scalaz.{INil, Semigroup, \/, \/-, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._
import scalaz.Semigroup._
import scalaz.syntax.semigroup._
import scalaz.syntax.monad._
import stacc.logic.Ev._
import stacc.math.domain

object Unification {

  def unify(resolve: (Ref \/ PSet) => Ev[Prop])(ps: NEL[Prop]): Ev[Prop] = {
    ps.tail.foldLeft[Ev[Prop]](Top(ps.head)) {
      case (ev, b) => ev.flatMap(a => unifyProps(resolve)(a, b))
    }
  }

  def unifyProps(resolve: (Ref \/ PSet) => Ev[Prop])(a: Prop, b: Prop): Ev[Prop] = (a, b) match {
    case (MemberOf(\/-(as: PSet)), MemberOf(\/-(bs: PSet))) => Congruent(as, bs).eval(resolve).map(p => ee(p))
    case (Equals(\/-(as: PSet)), Equals(\/-(bs: PSet))) => Equal(as, bs).eval.map(p => :=(p))
    case (_, _) => ???
  }


}

