package stacc.ast

import scalaz.{IList, \/, NonEmptyList => NEL}
import stacc.logic.{Bottom, Ev, Lie, Top}
import AstSyntax._
import stacc.ast.PSet.ConcPSet

import scala.collection.Set

object Project {
 def project(v: Var)(ps: PSet): Ev[NEL[Prop[Ref \/ PSet]]] = {
    ps.asInstanceOf[ConcPSet].vs.filter(_.v == v).map(_.p).toList match {
      case h :: xs => Top(NEL.nel(h, IList.fromList(xs)))
      case Nil => Bottom(Lie(v ee(ps)))
    }
 }

}
