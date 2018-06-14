package stacc.ast
import scalaz.{-\/, NonEmptyList, \/, \/-}
import stacc.ast._

object AstSyntax {

  def :=(ps: PSet):  Equals[PSet] = Equals(ps)
  def :=(s: Symbol): Equals[Ref \/ PSet] = Equals(-\/(Ref(Path(s.name))))
  def :=(ref: Ref):  Equals[Ref] = Equals(ref)

  def ee(ps: PSet):  MemberOf[PSet] = MemberOf(ps)
  def ee(s: Symbol): MemberOf[Ref] = MemberOf(Ref(Path(s.name)))
  def ee(ref: Ref):  MemberOf[Ref] = MemberOf(ref)
  def ee(psOrRef: Ref \/ PSet):  MemberOf[Ref \/ PSet] = MemberOf(psOrRef)

  implicit def idToVar(id: Symbol): Var = Var(id.name)
  implicit def idToRef(id: Symbol): Ref = Ref(Path(id.name))

  implicit def varOpSyntax(vv: Var): OpsOnVar = new OpsOnVar {
    override val v: Var = vv
  }

  implicit def symbolOpValueSyntax(s: Symbol): OpsOnValue = new OpsOnValue {
    override val lhs: Ref \/ PSet = -\/(Ref(s.name))
  }
  implicit def symbolOpVarSyntax(s: Symbol): OpsOnVar = new OpsOnVar {
    override val v: Var = Var(s.name)
  }

  implicit def psetOpValueSyntax(pset: PSet): OpsOnValue = new OpsOnValue {
    override val lhs: Ref \/ PSet = \/-(pset)
  }

  trait OpsOnVar {
    val v: Var

    def :=(b: Symbol) = PropOnVar(Var(v.name), Equals(-\/(Ref(Path(b.name)))))
    def :=(b: PSet)  = PropOnVar(Var(v.name),  Equals(\/-(b)))
    def :=(b: Ref)  = PropOnVar(Var(v.name),  Equals(-\/(b)))

    def ee(s: Symbol) = PropOnVar(Var(v.name),  MemberOf(-\/(Ref(Path(s.name)))))
    def ee(ps: PSet)  = PropOnVar(Var(v.name),  MemberOf(\/-(ps)))
    def ee(ref: Ref)  = PropOnVar(Var(v.name),  MemberOf(-\/(ref)))
    def ee(s: Ref \/ PSet)  = PropOnVar(Var(v.name),  MemberOf(s))

   }

  trait OpsOnValue {
    val lhs: Ref \/ PSet

    def u(s: Symbol) = Union(lhs,  -\/(Ref(Path(s.name))))
    def u(ps: PSet)  = Union(lhs,  \/-(ps))
    def u(ref: Ref)  = Union(lhs,  -\/(ref))
    def u(rhs: Ref \/ PSet)  = Union(lhs, rhs)
  }

  implicit class ElemOps(e: PSet) {
 //  def U(other: PSet): PropUnion = PropUnion(e, other)
  }


}
