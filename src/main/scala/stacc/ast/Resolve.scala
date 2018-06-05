package stacc.ast

import stacc.logic.{Bottom, Ev, Top}
import stacc.logic.Unification.unify
import AstSyntax._
import Project.project
import stacc.logic.Ev._
import stacc.logic.Ev
import scalaz.{NonEmptyList => NEL, _}
import stacc.math.domain
import Scalaz._
object Resolve {

  def resolve(root: PSet)(p: Ref \/ PSet): Ev[Prop] = p match {
    case -\/(Ref(path)) => resolvePath(path)(root)(\/-(root))
    case \/-(pset)      => Top(Equals(\/-(pset)))
  }

  def resolvePath(path: Path)(root: PSet)(focus: Ref \/ PSet): Ev[Prop] = focus match {
    case \/-(pset) =>
      path.split match {
        case (nextName, Some(restPath)) =>
          val projected = project(Var(nextName))(pset)
          val nextTarget: Ev[Ref \/ PSet] = projected.flatMap(unify(resolve(root))).flatMap(domain)
          val rr =nextTarget.flatMap(resolvePath(restPath)(root))
          rr
        case (lastName, None) => project(Var(lastName))(pset).flatMap(unify(resolve(root)))
      }
    case -\/(ref) =>
      for {
        resolvedRef <- resolve(root)(-\/(ref))
        target <- domain(resolvedRef)
        prop <- resolvePath(path)(root)(target)
      } yield prop
  }

}
