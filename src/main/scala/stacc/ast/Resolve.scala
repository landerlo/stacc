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
import scalaz.syntax.monad._
import Prop._

object Resolve {

  def resolve(root: PSet)(p: Ref \/ PSet): Ev[Prop[PSet]] = p match {
    case -\/(Ref(path)) => resolvePath(path)(root)(\/-(root))
    case \/-(pset)      => Top(Equals(pset))
  }

  def resolvePath(path: Path)(root: PSet)(focus: Ref \/ PSet): Ev[Prop[PSet]] = ???
//  focus match {
//    case \/-(pset) =>
//      path.split match {
//        case (nextName, Some(restPath)) =>
//          val projected: Ev[NEL[Prop[Disjunction[Ref, PSet]]]] = project(Var(nextName))(pset)
//          val nextTarget: Ev[Prop[PSet]] = projected.flatMap(unify(resolve(root)) _)
//
//            val nn = nextTarget.map(x => x.map(\/-(_))).flatMap(domain)
//          val rr =nextTarget.flatMap(resolvePath(restPath)(root))
//          rr
//        case (lastName, None) =>
//          val projected = project(Var(lastName))(pset)
//          val nextTarget: Ev[Ref \/ PSet] = projected.flatMap(unify(resolve(root))).flatMap(domain)
//
//          nextTarget.flatMap {
//            case \/-(p: PSet) => Top(Equals(p))
//            case -\/(r: Ref) => resolve(root)(-\/(r))
//          }
//       }
//    case -\/(ref) =>
//      for {
//        resolvedRef <- resolve(root)(-\/(ref))
//        target <- domain(resolvedRef.map(\/-(_)))
//        prop <- resolvePath(path)(root)(target)
//      } yield prop
//  }

}
