package stacc.ast
import scalaz.{-\/, NonEmptyList, \/, \/-}
import stacc.ast._

object AstSyntax {

  def ee(ps: PSet)  = AMember(ps)
  def ee(s: Symbol) = AMember(Ref(Path(s.name)))
  def ee(ref: Ref)  = AMember(ref)

  implicit def idToVar(id: Symbol): Var = Var(id.name)
  implicit def idToRef(id: Symbol): Ref = Ref(Path(id.name))

  implicit def varOpSyntax(vv: Var): OpsOnVar = new OpsOnVar {
    override val v: Var = vv
  }

  implicit def symbolOpValueSyntax(s: Symbol): OpsOnValue = new OpsOnValue {
    override val lhs: PSet = Ref(s.name)
  }
  implicit def symbolOpVarSyntax(s: Symbol): OpsOnVar = new OpsOnVar {
    override val v: Var = Var(s.name)
  }

  implicit def psetOpValueSyntax(pset: PSet): OpsOnValue = new OpsOnValue {
    override val lhs: PSet = pset
  }

  trait OpsOnVar {
    val v: Var

    def :=(b: Symbol) = PropOnVar[PSet](Var(v.name),  Ref(Path(b.name)))
    def :=(b: PSet)   =  PropOnVar[PSet](Var(v.name),  b)
    def :=(b: Ref)    =   PropOnVar[PSet](Var(v.name),  b)

    def ee(s: Symbol) = PropOnVar[PSet](Var(v.name),  AMember(Ref(Path(s.name))))
    def ee(ps: PSet)  = PropOnVar[PSet](Var(v.name),  AMember(ps))
    def ee(ref: Ref)  = PropOnVar[PSet](Var(v.name),  AMember(ref))

   }

  trait OpsOnValue {
    val lhs: PSet

    def u(s: Symbol) = Union(lhs,  Ref(Path(s.name)))
    def u(ps: PSet)  = Union(lhs,  ps)
    def u(ref: Ref)  = Union(lhs,  ref)
  }

  implicit class ElemOps(e: PSet) {
 //  def U(other: PSet): PropUnion = PropUnion(e, other)
  }


}
