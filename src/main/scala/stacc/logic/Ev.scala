package stacc.logic


trait Ev[A]
case class Top[A](a: A) extends Ev[A]
case class Bottom[A](a: A) extends Ev[A]
case class Canon[A](a: A) extends Ev[A]

object Ev {

  trait Functor[A] {
    def map[B](a: A)(f: A => B): B
  }

}
