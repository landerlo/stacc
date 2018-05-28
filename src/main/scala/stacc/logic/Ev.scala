package stacc.logic

import stacc.ast.{LogicPred, Prop, PropOnVar}
import scalaz.Monad

trait Lie
case class LyingPropOnVar(lie: PropOnVar) extends Lie
case class LyingProp(lie: Prop) extends Lie
case class LyingLogicPred(lie: LogicPred) extends Lie

object Lie {
  def apply(p: Prop): Lie = LyingProp(p)
  def apply(pov: PropOnVar): Lie = LyingPropOnVar(pov)
  def apply(pred: LogicPred): Lie = LyingLogicPred(pred)
}

trait Ev[A]
case class Top[A](a: A) extends Ev[A]
case class Bottom[A](lie: Lie) extends Ev[A]
case class Canon[A](a: A) extends Ev[A]

object Ev {

  implicit val EvMonad: Monad[Ev] = new Monad[Ev] {
    override def bind[A, B](fa: Ev[A])(f: A => Ev[B]): Ev[B] = fa match {
      case Top(a) => f(a)
      case Bottom(lie) => Bottom[B](lie)
    }

    override def point[A](a: => A): Ev[A] = Top(a)
  }

}
