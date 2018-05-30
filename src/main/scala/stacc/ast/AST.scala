package stacc.ast

import scalaz.{IList, NonEmptyList => NEL, _}
import Scalaz._
import scala.collection.Set
case class Id(s: String)

case class Ref(path: Path)
object Ref {
  def apply(path: String): Ref = Ref(Path(path))
}

trait PSet

case object SET extends PSet

object PSet {
  def apply(vs: Set[PropOnVar]): PSet = ConcPSet(vs)
  def apply(vs: PropOnVar*): PSet = PSet(vs.toSet)

  case class ConcPSet(vs: Set[PropOnVar]) extends PSet

  val empty = ConcPSet(Set[PropOnVar]())
  val Ø = empty
}



case class PropOnVar(v: Var, p: Prop)

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


