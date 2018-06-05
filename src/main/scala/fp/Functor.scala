package fp


trait Reify[F[_], A]
case class Pure[F[_], A](fa: F[A]) extends Reify[F, A]
case class Map[F[_], A, B](a: Reify[F, A], f: A => B) extends Reify[F, B]
case class FlatMap[F[_], A, B](a: Reify[F, A], f: A => F[B]) extends Reify[F, B]



object ID {
  type ID[A] = A


}
