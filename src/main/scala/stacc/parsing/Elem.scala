package stacc.parsing.ast

trait Obj
case class Id(id:  String) extends Elem
case class Ref(id: String) extends Elem
trait Elem extends Obj

trait Set extends Elem
case class LitSet(elems: Seq[Elem]) extends Set
case class Union(a: Set, b: Set) extends Set
case object Empty extends Set
case class SetRef(setRef: Id) extends Set

trait Pred extends Obj
case class Eq(a: Elem, b: Elem) extends Pred



case class Disj(elems: Elem*) extends Elem
