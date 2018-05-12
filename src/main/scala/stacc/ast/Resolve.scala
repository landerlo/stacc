package stacc.ast

import stacc.logic.{Bottom, Ev, Top}
import stacc.logic.Unification.unify
import AstSyntax._
import Project.project
import stacc.logic.Ev._
import stacc.logic.Ev
import scalaz.{NonEmptyList => NEL, _}
import Scalaz._
object Resolve {

  def resolve(path: Path)(root: PSet)(focus: Ref \/ PSet): Ev[Prop] = focus match {
    case \/-(focusPSet) =>
      path.split match {
        case (nextName, Some(restPath)) =>
            val projected  = project(Var(nextName))(focusPSet)
            val nextTarget = projected.flatMap(unify).map(_.target)
            nextTarget.flatMap(resolve(restPath)(root))

        case (lastName, None) => project(Var(lastName))(focusPSet).flatMap(unify)
      }

    case -\/(ref)  =>
      val resolvedRef: Ev[Prop] = resolve(path)(root)(\/-(root))
      resolvedRef.flatMap(prop => resolve(path)(root)(prop.target))
  }
}
