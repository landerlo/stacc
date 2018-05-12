package stacc.logic

import stacc.ast.PSet

object Unification {

  trait PSetUnification[A <: PSet] {
    def unify(a: A): A
  }

 def unify(ps: Seq[PSet]): Seq[PSet] = ps match {
   case Seq() => Seq()
   case a => a
   // case (ConcreteVarPSet(as), ConcreteVarPSet(bs)) => ConcreteVarPSet(as ++ bs)
    // case _ => ConcreteVarPSet.empty
 }

//   a match {
//     case PropUnion(us) =>
//       val unified = us.map(unify)
//       unified.reduce(u)
//   }
// }
}
