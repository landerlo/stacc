package stacc.ast

import scalaz.{IList, NonEmptyList => NEL}
import stacc.logic.{Bottom, Ev, Top}
import AstSyntax._

import scala.collection.Set

object Project {
 def project(v: Var)(ps: PSet): Ev[NEL[Prop]] = {
    ps.vs.filter(_.v == v).map(_.p).toList match {
      case h :: xs => Top(NEL.nel(h, IList.fromList(xs)))
      case Nil => Bottom(v ee(ps))
    }
 }

}
