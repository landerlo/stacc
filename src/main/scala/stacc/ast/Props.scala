package stacc.ast

import stacc.util.NELExtension.distinct
import scalaz.{IList, INil, Order, \/, NonEmptyList => NEL}
import stacc.logic._
import stacc.logic.Ev._


import scalaz._, Scalaz._
import stacc.ast.CondEquals.Case
import std.list._
//import syntax.traverse._

object CondEquals {
  case class Case[T](cond: PSet, target: T)
}

/*
  These predicates are not in the predicates on var of the calulus,
  but instrumental predicates to evaluate in the calculus.
  The separation from Prop is purely to keep Prop as the only predicates
  expected in the grammar.
 */
sealed trait LogicPred
case class Congruent(a: ConcPSet, b: ConcPSet) extends LogicPred {
  def eval(resolve: PSet => Ev[PSet]): Ev[PSet] = {
    val intersection = a.vs.map(_.v).intersect(b.vs.map(_.v))

    val congruentShared: IList[Ev[PropOnVar[PSet]]] = IList.fromList(intersection.toList).map { commonV =>
      //TODO: remove horrible gets and improve finding of properties
         val pa: Ev[PSet] = resolve(a.vs.find(_.v == commonV).get.p)
         val pb: Ev[PSet] = resolve(b.vs.find(_.v == commonV).get.p)
      val un: Ev[PropOnVar[PSet]] =   for {
           ra <- pa
           rb <- pb
           puni <- Unification.unifyProps(resolve)(ra, rb)
        } yield PropOnVar[PSet](commonV, puni)
      println(un)
      un
    }

    val cong: Ev[IList[PropOnVar[PSet]]] = congruentShared.sequence
    val notInIntersection = (pset: ConcPSet) => pset.vs.filter(pov => !intersection.contains(pov.v))
    cong.map(c => PSet(c.toList.toSet ++ notInIntersection(a) ++ notInIntersection(b)))
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
