package stacc.ast

import scalaz.{IList, NonEmptyList => NEL, _}
import Scalaz._
import scala.collection.Set
case class Id(s: String)

sealed trait Rank
case object RankOne extends Rank


sealed trait PSet
case class ConcPSet(vs: Set[PropOnVar[PSet]]) extends PSet
case class AMember(of: PSet) extends PSet

object AMember {
  val A_SET = AMember(SET)
}
case object SET extends PSet

case class Union(a: PSet, b: PSet) extends PSet
case class Ref(path: Path) extends PSet

object PSet {
  def apply(vs: Set[PropOnVar[PSet]]): PSet = ConcPSet(vs)
  def apply(vs: PropOnVar[PSet]*): PSet = PSet(vs.toSet)

  val empty = ConcPSet(Set[PropOnVar[PSet]]())
  val Ø = empty
}

object Ref {
  def apply(path: String): Ref = Ref(Path(path))
}

case class PropOnVar[T](v: Var, p: T)

object PropOnVar {
}

case class Path(segments: NEL[String]) {
  import scalaz.IList._

  def split: (String, Option[Path]) = segments match {
    case NEL(a, INil()) => (a, None)
    case NEL(a, ICons(b: String, rem: IList[String])) => (a, Some(Path(NEL.nel(b, rem))))
  }
}



object Path {
   def apply(path: String): Path = {
     //Unchecked as split always returns itself at least it will always be a NEL
     val nel = (path.split("\\.").toList: @unchecked) match {
       case a :: rest => NEL.nel(a, IList.fromList(rest))
     }
     Path(nel)
   }
}

//case class Union(a: Either[Ref, VarPSet], b: Either[Ref, VarPSet]) extends PSet

case class Absurdity()

case class Var(name: String)

case class Universe(root: PSet)


