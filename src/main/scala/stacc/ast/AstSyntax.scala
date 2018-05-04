package stacc.ast
import stacc.ast._

object AstSyntax {

  implicit def idToElem(id: Symbol): PSet = Ref(Path(id.name))
  implicit class VariOpes(a: Symbol) {
    def :=(b: Symbol) = PropOnVar(a.name, Ref(Path(b.name)))
    def :=(b: PSet)  = PropOnVar(a.name, b)
//    def U(other: PSet): Union = Union(Ref(Path(a.name)), other)

//    def ee(setId: Symbol) = PropOnVar(Id(a), MemberOf(ISetRef(Path(setId.name))))
//    def ee(set: PSet)   = PropOnVar(Id(a), MemberOf(set))

//    def :|:(other: PSet): Or = Or(ISetRef(Path(a.name)), other)
  }


  implicit class ElemOps(e: PSet) {
 //  def U(other: PSet): PropUnion = PropUnion(e, other)
  }


}
