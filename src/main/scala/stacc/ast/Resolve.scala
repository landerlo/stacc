package stacc.ast

import stacc.logic.{Bottom, Ev, Top}
import stacc.logic.Unification.unify
import AstSyntax._
import Project.project
import stacc.logic.Ev._
import stacc.logic.Ev
import scalaz.{NonEmptyList => NEL, _}
import scalaz._
import scalaz.syntax.monad._
import stacc.math.domain

object Resolve {
//  import Prop.traverseProp

  import Ev.EvMonad
  def resolve(root: PSet)(p: PSet): Ev[PSet] = p match {
    case Ref(path) => (resolvePath(path)(root)(root)).flatMap(resolve(root))
    case pset      => Top(pset)
  }

  def resolvePath(path: Path)(root: PSet)(focus: PSet): Ev[PSet] =
  focus match {
    case ref: Ref =>
      for {
        resolvedRef <- resolve(root)(ref)
        target <- domain(resolvedRef)
        prop <- resolvePath(path)(root)(target)
      } yield prop
     case pset: PSet =>
      path.split match {
        case (nextName, Some(restPath)) =>
          val projected: Ev[NEL[PSet]] = project(Var(nextName))(pset)

          val nextTarget: Ev[PSet] = projected.flatMap(unify(resolve(root)) _).flatMap(resolve(root))
          val dom = nextTarget.flatMap(domain)
          dom.flatMap(resolvePath(restPath)(root))

        case (lastName, None) =>
          val projected = project(Var(lastName))(pset)
          projected.flatMap(unify(resolve(root))) match {
            case Top(Ref(ref)) => resolvePath(ref)(root)(root)
            case Top(AMember(Ref(ref))) => resolvePath(ref)(root)(root).map(AMember(_))
            case Top(pset) => Top(pset)
            case b: Bottom[PSet] => b
          }
       }
 }
}
