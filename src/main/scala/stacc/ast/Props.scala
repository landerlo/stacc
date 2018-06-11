package stacc.ast

import stacc.util.NELExtension.distinct
import scalaz.{IList, INil, Order, \/, NonEmptyList => NEL}
import stacc.logic._
import stacc.logic.Ev._
import scalaz._
import stacc.ast.CondEquals.Case
import stacc.ast.PSet.ConcPSet
import std.list._
import syntax.traverse._

sealed trait Prop
case class Equals(target: Ref \/ PSet)   extends Prop
case class CondEquals(cases: Seq[Case])  extends Prop
case class MemberOf(target: Ref \/ PSet) extends Prop

object CondEquals {
  case class Case(cond: PSet, target: Ref \/ PSet)
}

/*
  These predicates are not in the predicates on var of the calulus,
  but instrumental predicates to evaluate in the calculus.
  The separation from Prop is purely to keep Prop as the only predicates
  expected in the grammar.
 */
sealed trait LogicPred
case class Congruent(a: ConcPSet, b: ConcPSet) extends LogicPred {
  def eval(resolve: Ref \/ PSet => Ev[Prop]): Ev[PSet] = {
    val intersection = a.vs.map(_.v).intersect(b.vs.map(_.v))

    val congruentShared: Ev[IList[PropOnVar]] = IList.fromList(intersection.toList).map { commonV =>
         val pa = a.vs.find(_.v == commonV).get.p
         val pb = b.vs.find(_.v == commonV).get.p
         println(pa)
      println(pb)
      val un =   for {
           puni <- Unification.unifyProps(resolve)(pa, pb)
        } yield PropOnVar(commonV, puni)
      println(un)
      un
    }.sequenceU

    val notInIntersection = (pset: ConcPSet) => pset.vs.filter(pov => !intersection.contains(pov.v))

    congruentShared.map(cong => PSet(cong.toList.toSet ++ notInIntersection(a) ++ notInIntersection(b)))
  }
}

case class Equal[A](a: A, b: A) extends LogicPred {
  def eval(implicit eq: Equiv[A]): Ev[A] =
    if (eq.equiv(a, b))
      Top(a)
    else
      Bottom(Lie(this))
}

case class AllEqual[A](as: NEL[A]) extends LogicPred {
  def eval(implicit eq: Equiv[A]): Ev[A] = distinct(as) match {
    case NEL(unique, INil()) => Top(unique)
    case distincts => Bottom(Lie(AllEqual(distincts)))
  }
}
