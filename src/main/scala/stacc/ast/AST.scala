package stacc.ast

import scalaz.{NonEmptyList => NEL, \/}

import scala.collection.Set
case class Id(s: String)

case class Ref(path: Path)
case class PSet(vs: Set[PropOnVar])

object PSet {
   val empty = PSet(Set[PropOnVar]())
   val ∅ = empty

   def apply(vs: PropOnVar*): PSet = PSet(vs.toSet)
}

trait Prop
case class Equals(e: Ref \/ PSet) extends Prop
case class MemberOf(e: Ref \/ PSet) extends Prop

case class PropOnVar(v: Var, p: Prop)

object PropOnVar {
}

case class Path(segments: List[String]) {
  require(segments.nonEmpty)
  def split: (String, Option[Path]) = segments match {
    case a :: Nil => (a, None)
    case a :: rem => (a, Some(Path(rem)))
    case Nil => throw new IllegalStateException("NEL")
  }
}


object Path {
   def apply(path: String): Path = Path(path.split("\\.").toList)
}

//case class Union(a: Either[Ref, VarPSet], b: Either[Ref, VarPSet]) extends PSet

case class Absurdity()

case class Var(name: String)

case class Universe(root: PSet)


