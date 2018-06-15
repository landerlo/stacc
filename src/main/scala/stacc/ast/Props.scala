package stacc.ast

import stacc.util.NELExtension.distinct
import scalaz.{IList, INil, Order, \/, NonEmptyList => NEL}
import stacc.logic._
import stacc.logic.Ev._
import Prop.propMonad


import scalaz._
import stacc.ast.CondEquals.Case
import stacc.ast.PSet.ConcPSet
import std.list._
//import syntax.traverse._
import syntax.monad._

import Prop._

sealed trait Prop[T]
case class Equals[T](target: T)  extends Prop[T]
case class CondEquals[T](cases: Seq[Case[T]])  extends Prop[T]
case class MemberOf[T](target: T) extends Prop[T]

object Prop {
  implicit val propMonad: Monad[Prop] = new Monad[Prop] {
    override def bind[A, B](fa: Prop[A])(f: A => Prop[B]): Prop[B] = fa match {
      case Equals(a) => f(a) match {
        case Equals(b) => Equals(b)
        case CondEquals(cases) => ???
        case MemberOf(setB) => MemberOf(setB)
      }
      case CondEquals(setA) => ???
      case MemberOf(setA) => f(setA) match {
        case Equals(setB) => MemberOf(setB)
        case CondEquals(cases) => ???
        case MemberOf(setB) => MemberOf(setB)
      }
    }

    override def point[A](a: => A): Prop[A] = Equals(a)
  }

//  implicit val traverseProp: Traverse[Prop] = new Traverse[Prop] {
//    override def traverseImpl[G[_], A, B](fa: Prop[A])(f: A => G[B])(implicit evidence$1: Applicative[G]): G[Prop[B]] = fa match {
//      case Equals(a) =>
//        val outerA: G[B] = f(a)
//        evidence$1.map(outerA)(Equals(_))
//      case MemberOf(a) =>
//        val outerA: G[B] = f(a)
//        evidence$1.map(outerA)(MemberOf(_))
//      case CondEquals(cases) => ???
//
//    }
//  }
}
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
  def eval(resolve: Ref \/ PSet => Ev[Prop[PSet]]): Ev[PSet] = {
    val intersection = a.vs.map(_.v).intersect(b.vs.map(_.v))

    val congruentShared: Ev[IList[PropOnVar[Ref \/ PSet]]] = IList.fromList(intersection.toList).map { commonV =>
      //TODO: remove horrible gets and improve finding of properties
         val pa = a.vs.find(_.v == commonV).get.p.map(resolve)
         val pb = b.vs.find(_.v == commonV).get.p.map(resolve)
      val un =   for {
           ra <- pa.map(resolve)
           rb <- pa.map(resolve)
           puni <- Unification.unifyProps(resolve)(ra, rb)
        } yield PropOnVar[PSet](commonV, puni)
      println(un)
    }

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
