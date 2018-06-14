package stacc.ast

import scalaz.{IList, NonEmptyList => NEL, _}
import Scalaz._
import scala.collection.Set
case class Id(s: String)

case class Ref(path: Path)
object Ref {
  def apply(path: String): Ref = Ref(Path(path))
}

sealed trait PSet
case object SET extends PSet
case object A_SET extends PSet
case class Union(a: Ref \/ PSet, b: Ref \/ PSet) extends PSet

object PSet {
  def apply(vs: Set[PropOnVar[Ref \/ PSet]]): PSet = ConcPSet(vs)
  def apply(vs: PropOnVar[Ref \/ PSet]*): PSet = PSet(vs.toSet)

  case class ConcPSet(vs: Set[PropOnVar[Ref \/ PSet]]) extends PSet

  val empty = ConcPSet(Set[PropOnVar[Ref \/ PSet]]())
  val Ø = empty
}

case class PropOnVar[T](v: Var, p: Prop[T])

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


