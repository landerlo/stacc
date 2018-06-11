package stacc.resolution

import scalaz.{-\/, \/, \/-}
import stacc.ast.PSet.ConcPSet
import stacc.ast.Project.project
import stacc.ast.Resolve.resolve
import stacc.ast._
import stacc.logic.Unification.unify
import stacc.logic.{Bottom, Ev, SETCanBeUnioned, Top}
import stacc.math.domain

object Canon {

 def canonical(resolve: Ref \/ PSet => Ev[Prop])(ev: PSet): Ev[PSet] = {
  ev match {
   case ConcPSet(pset) => ???

   case Union(\/-(psetA: ConcPSet), \/-(psetB: ConcPSet)) => Top(ConcPSet(psetA.vs union psetB.vs))

   case Union(\/-(psetA: ConcPSet), -\/(refB)) => ???
   case Union(\/-(psetA: ConcPSet), \/-(Union(a, b))) => ???
   case Union(-\/(refA), \/-(psetB: ConcPSet)) => ???
   case Union(-\/(refA), \/-(Union(a, b))) => ???

   case Union(\/-(Union(a, b)), \/-(psetA: ConcPSet)) => ???
   case Union(\/-(Union(a, b)), -\/(refB)) => ???

   case Union(-\/(refA), -\/(refB)) => ???
   case Union(\/-(Union(a, b)), \/-(Union(c, d)))=> ???

   case a @ Union(\/-(ASET), _) => Top(a)
   case a @ Union(_, \/-(ASET)) => Top(a)

   case Union(\/-(SET), _) => Bottom(SETCanBeUnioned)
   case Union(_, \/-(SET)) => Bottom(SETCanBeUnioned)

   case ASET => Top(ASET)
   case SET => Top(SET)
  }
 }

}
