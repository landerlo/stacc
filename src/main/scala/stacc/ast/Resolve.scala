package stacc.ast

import stacc.recursion.Iteration.iterateUntilStable
import TraverseGraph._

object Resolve {
  def resolve(path: Path, root: PSet): Option[PSet] = {

    def follow(focus: PSet): Option[PSet] = {
            val res = focus match {
              case Ref(path) => resolve(path, root).flatMap(follow).orElse[PSet](Some(Absurdity()))
              case p: PSet => Some(p)
            }
            println(s"follow $focus -> $res")
            res
          }

          def subResolve(p: Path)(focus: PSet): Option[PSet] = {
            val res = (p.split, follow(focus)) match {
              case ((nextId, None), None) => None
              case ((nextId, None), Some(vp: ConcreteVarPSet)) => vp.getProp(nextId).flatMap(follow)
              case ((nextId, Some(remainingPath)), Some(vp: ConcreteVarPSet)) => vp.getProp(nextId).orElse(Some(Absurdity())).flatMap(subResolve(remainingPath)(_))
              case (_, None) => println("nowhere"); None //
              case (_, r @ Some(ps)) => println(s"something $ps"); r  //
            }
            println(s"Resolve: $p ${p.split} $focus -> $res")
            res
          }
          subResolve(path)(root)
    }
}
