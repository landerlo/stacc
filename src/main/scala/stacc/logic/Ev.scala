package stacc.logic

import stacc.ast.{LogicPred, PropOnVar}
import scalaz._
import scalaz.syntax.monad._

trait Lie
case class LyingPropOnVar[L](lie: PropOnVar[L]) extends Lie
case class LyingLogicPred(lie: LogicPred) extends Lie
case object SETCanBeUnioned extends Lie

object Lie {
  def apply[L](pov: PropOnVar[L]): Lie = LyingPropOnVar(pov)
  def apply(pred: LogicPred): Lie = LyingLogicPred(pred)
}

trait Ev[+A]
case class Top[+A](a: A) extends Ev[A]
case class Bottom[+A](lie: Lie) extends Ev[A]

object Ev {
  def top[A](a: A): Ev[A] = Top[A](a)

  implicit val EvMonad: Monad[Ev] = new Monad[Ev] {
    override def bind[A, B](fa: Ev[A])(f: A => Ev[B]): Ev[B] = fa match {
      case Top(a) => f(a)
      case Bottom(lie) => Bottom[B](lie)
    }

    override def point[A](a: => A): Ev[A] = Top(a)
  }

//  implicit val EvTraversable: Traverse1[Ev] = new Traverse1[Ev] {
//    override def traverse1Impl[G[_], A, B](fa: Ev[A])(f: A => G[B])(implicit evidence$2: Apply[G]): G[Ev[B]] = {
//
//    }
//
//    override def foldMapRight1[A, B](fa: Ev[A])(z: A => B)(f: (A, B) => B): B = {
//
//    }
//  }

}
