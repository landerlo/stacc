package stacc.resolution

import scalaz.{-\/, \/, \/-}
import stacc.ast.Project.project
import stacc.ast.Resolve.resolve
import stacc.ast._
import stacc.logic.Unification.unify
import stacc.logic.{Bottom, Ev, SETCanBeUnioned, Top}
import stacc.math.domain
import scalaz.syntax.monad._
import AMember.A_SET

object Canon {

 def canonical(resolve: Ref => Ev[PSet])(ev: PSet): Ev[PSet] = {
  ev match {
   case ConcPSet(pset) => ???

   case Union(psetA: ConcPSet, psetB: ConcPSet) => Top(ConcPSet(psetA.vs union psetB.vs))

   case Union(psetA: ConcPSet, refB: Ref) => ???
   case Union(psetA: ConcPSet, Union(a, b)) => ???
   case Union(refA: Ref, psetB: ConcPSet) => ???
   case Union(refA: Ref, Union(a, b)) => ???

   case Union(Union(a, b), psetA: ConcPSet) => ???
   case Union(Union(a, b), refB: Ref) => ???

   case Union(refA: Ref, refB: Ref) => ???
   case Union(Union(a, b), Union(c, d))=> ???

   case a @ Union(A_SET, _) => Top(a)
   case a @ Union(_, A_SET) => Top(a)

   case Union(SET, _) => Bottom(SETCanBeUnioned)
   case Union(_, SET) => Bottom(SETCanBeUnioned)

   case AMember(Ref(ref)) => resolve(Ref(ref)).map(AMember(_))
   case A_SET => Top(A_SET)
   case AMember(of) => Top(AMember(of))
   case SET => Top(SET)
   case ref: Ref => resolve(ref)
   case Union(a, b) => ???
  }
 }

}
