package stacc.util

import scalaz.{NonEmptyList => NEL, INil}

object NELExtension {

 def distinct[A](as: NEL[A])(implicit equiv: Equiv[A]): NEL[A] =
   as.tail.foldLeft(NEL.nels(as.head)) {
    case (dist, b) =>
      if(!dist.list.toList.exists(equiv.equiv(_, b)))
        NEL.nel[A](b, dist.list)
      else
        dist
  }.reverse

  def exist[A](as: NEL[A])(implicit equiv: Equiv[A]): NEL[A] = ???
}
