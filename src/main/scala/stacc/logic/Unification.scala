package stacc.logic

import scalaz.{NonEmptyList => NEL}
import stacc.ast.{AstSyntax, MemberOf, Prop}
import stacc.ast.AstSyntax._

object Unification {

 def unify(ps: NEL[Prop]): Ev[Prop] = ???

 implicit val unificationSemigroup = new Semigroup[Prop] {
  override def append(a: Prop, b: Prop): Prop =
   case (MemberOf(psA), MemberOf(psB)) =>
      for {
        a <-
      }
  }
 }
}
