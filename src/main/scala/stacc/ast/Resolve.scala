package stacc.ast

import stacc.logic.{Bottom, Ev, Top}
import stacc.logic.Unification.unify
import AstSyntax._
import Project.project
import stacc.logic.Ev._
import stacc.logic.Ev
import scalaz.{NonEmptyList => NEL, _}
import stacc.math.domain
import scalaz._
import scalaz.syntax.monad._

object Resolve {
//  import Prop.traverseProp

  import Ev.EvMonad
  def resolve(root: PSet)(p: Ref \/ PSet): Ev[Prop[PSet]] = p match {
    case -\/(Ref(path)) => resolvePath(path)(root)(\/-(root))
    case \/-(pset)      => Top(Equals(pset))
  }

  def resolvePath(path: Path)(root: PSet)(focus: Ref \/ PSet): Ev[Prop[PSet]] =
  focus match {
    case \/-(pset) =>
      path.split match {
        case (nextName, Some(restPath)) =>
          val projected: Ev[NEL[Prop[Disjunction[Ref, PSet]]]] = project(Var(nextName))(pset)
          val resolved: Ev[NEL[Prop[PSet]]] = ???

          val nextTarget: Ev[Prop[PSet]] = resolved.flatMap(unify(resolve(root)) _)

            val nn: Ev[Ref \/ PSet] = nextTarget.flatMap(domain)
          val rr = nn.flatMap(resolvePath(restPath)(root))
          rr
        case (lastName, None) =>
          val projected = project(Var(lastName))(pset)
          val resolved: Ev[NEL[Prop[PSet]]] = ???
          val nextTarget: Ev[Ref \/ PSet] = resolved.flatMap(unify(resolve(root))).flatMap(domain)

          nextTarget.flatMap {
            case \/-(p: PSet) => Top(Equals(p))
            case -\/(r: Ref) => resolve(root)(-\/(r))
          }
       }
    case -\/(ref) =>
      for {
        resolvedRef <- resolve(root)(-\/(ref))
        target <- domain(resolvedRef)
        prop <- resolvePath(path)(root)(target)
      } yield prop
  }

}
