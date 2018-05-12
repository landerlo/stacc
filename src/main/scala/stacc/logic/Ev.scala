package stacc.logic

import stacc.ast.PropOnVar
import scalaz.Monad

trait Ev[A]
case class Top[A](a: A) extends Ev[A]
case class Bottom[A](lie: PropOnVar) extends Ev[A]
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
