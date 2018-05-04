package stacc.ast

import Resolve.resolve

import scala.collection.Set
case class Id(s: String)

trait PSet

case class Props(ps: Set[PSet]) extends PSet
object Props {
  def apply(ps: PSet*): Props = Props(ps.toSet)
}
//case class Eq(p: PSet) extends PSet
case class Ref(path: Path) extends PSet
case class ConcreteVarPSet(vs: Set[PropOnVar]) extends PSet {
  def getProp(v: String): Option[PSet] = {
    val z: Seq[PSet] = vs.toSeq.filter(_.v == v).map(_.propsValue)
    z match {
      case Seq()    => None
      case Seq(p)   => Some(p)
      case multiple => Some(Props(multiple.toSet))
    }
  }
}

object ConcreteVarPSet {
   val empty = ConcreteVarPSet(Set[PropOnVar]())

   def apply(vs: PropOnVar*): ConcreteVarPSet = ConcreteVarPSet(vs.toSet)
}

case class PropOnVar(v: String, propsValue: PSet)

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

case class Absurdity() extends PSet

case class Var(name: String)

case class Universe(root: ConcreteVarPSet)

trait VarPropSet[A] {

  def resolveProps(ctx: Universe, a: A): Option[ConcreteVarPSet]
}


