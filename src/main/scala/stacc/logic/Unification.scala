package stacc.logic

import scalaz.{\/, NonEmptyList => NEL}
import stacc.ast._
import stacc.ast.AstSyntax._
import scalaz.syntax.monad._

object Unification {

 def unify(resolve: (Ref \/ PSet) => Ev[Prop])(ps: NEL[Prop]): Ev[Prop] = Top(ps.head)

 implicit def unificationSemigroup(resolve: Ref \/ PSet => Ev[PSet]) = new Semigroup[Prop] {
   override def append(a: Prop, b: Prop): Prop = (a, b) match {
     case (MemberOf(psA), MemberOf(psB)) =>
       val commonVars = for {
         a <- resolve(psA)
         b <- resolve(psB)
       } yield vars(a).intersect(vars(b))

       ???
     case _ => ???
   }
 }

  def vars(ps: PSet): Set[Var] = ???

}