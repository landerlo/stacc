package stacc.logic

import stacc.ast.{LogicPred, Prop, PropOnVar}
import scalaz.{Functor, Monad}

trait Lie
case class LyingPropOnVar[L](lie: PropOnVar[L]) extends Lie
case class LyingProp[L](lie: Prop[L]) extends Lie
case class LyingLogicPred(lie: LogicPred) extends Lie
case object SETCanBeUnioned extends Lie

object Lie {
  def apply[L](p: Prop[L]): Lie = LyingProp(p)
  def apply[L](pov: PropOnVar[L]): Lie = LyingPropOnVar(pov)
  def apply(pred: LogicPred): Lie = LyingLogicPred(pred)
}

trait Ev[A]
case class Top[A](a: A) extends Ev[A]
case class Bottom[A](lie: Lie) extends Ev[A]

object Ev {

  implicit val EvMonad: Monad[Ev] = new Monad[Ev] {
    override def bind[A, B](fa: Ev[A])(f: A => Ev[B]): Ev[B] = fa match {
      case Top(a) => f(a)
      case Bottom(lie) => Bottom[B](lie)
    }

    override def point[A](a: => A): Ev[A] = Top(a)
  }

}
