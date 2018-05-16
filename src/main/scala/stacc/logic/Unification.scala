package stacc.logic

import scalaz.{\/, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._

object Unification {

 def unify(resolve: (Ref \/ PSet) => Ev[PSet])(ps: NEL[Prop]): Ev[Prop] = ???

 implicit def unificationSemigroup(resolve: Ref \/ PSet => Ev[PSet]) = new Semigroup[Prop] {
  override def append(a: Prop, b: Prop): Prop = (a, b) match {
   case (MemberOf(psA), MemberOf(psB)) =>
      for {
        a <- resolve(psA)
        b <- resolve(psB)
        commonVars = vars(a).intersect(vars(b))

      }
  }
 }

  def vars(ps: PSet): Set[Var] = ???
}


{
  A e Set

  x e A

  x e { j = {} } u { _ e _ Set }
}


}