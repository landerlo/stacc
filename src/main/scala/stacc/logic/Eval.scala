package stacc.logic

import javax.management.StandardEmitterMBean
import stacc.ast._

trait Semigroup[A] {
  def append(a: A, b: A): A
}

object Semigroup {
  implicit def seqSemigroup[A]: Semigroup[Seq[A]] = new Semigroup[Seq[A]] {
    override def append(a: Seq[A], b: Seq[A]) = a ++ b
  }

}

trait Monoid[A] extends Semigroup[A] {
  def zero: A
}
object Monoid {
  implicit def setMonoid[A : Monoid]: Monoid[Seq[A]] = new Monoid[Seq[A]] {
    override def append(a: Seq[A], b: Seq[A]) = a ++ b

    override def zero = Seq()
  }

}

//import scalaz.syntax.SemigroupSyntax._
trait PropEval[+A]
case class Pending[+A] private (pending: A) extends PropEval[A]

