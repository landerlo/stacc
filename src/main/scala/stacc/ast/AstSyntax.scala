package stacc.ast
import scalaz.{-\/, NonEmptyList, \/-}
import stacc.ast._

object AstSyntax {

  def :=(ps: PSet):  Equals = Equals(\/-(ps))
  def :=(s: Symbol): Equals = Equals(-\/(Ref(Path(s.name))))
  def :=(ref: Ref):  Equals = Equals(-\/(ref))

  def ee(ps: PSet):  MemberOf = MemberOf(\/-(ps))
  def ee(s: Symbol): MemberOf = MemberOf(-\/(Ref(Path(s.name))))
  def ee(ref: Ref):  MemberOf = MemberOf(-\/(ref))

  implicit def idToVar(id: Symbol): Var = Var(id.name)
  implicit def idToRef(id: Symbol): Ref = Ref(Path(id.name))

  implicit def varOpSyntax(vv: Var): OpsOnVar = new OpsOnVar {
    override val v: Var = vv
  }

  implicit def symbolOpSyntax(s: Symbol): OpsOnVar = new OpsOnVar {
    override val v: Var = Var(s.name)
  }


  trait OpsOnVar {
    val v: Var

    def :=(b: Symbol) = PropOnVar(Var(v.name), Equals(-\/(Ref(Path(b.name)))))
    def :=(b: PSet)  = PropOnVar(Var(v.name),  Equals(\/-(b)))
    def :=(b: Ref)  = PropOnVar(Var(v.name),  Equals(-\/(b)))

    def ee(s: Symbol) = PropOnVar(Var(v.name),  MemberOf(-\/(Ref(Path(s.name)))))
    def ee(ps: PSet)  = PropOnVar(Var(v.name),  MemberOf(\/-(ps)))
    def ee(ref: Ref)  = PropOnVar(Var(v.name),  MemberOf(-\/(ref)))
  }


  implicit class ElemOps(e: PSet) {
 //  def U(other: PSet): PropUnion = PropUnion(e, other)
  }


}
