package stacc

import scalaz.{\/, \/-}
import stacc.ast._
import stacc.logic.{Ev, Top}
import AMember.A_SET

package object math {

// def domain(p: Prop[PSet]): Ev[PSet] = p match {
//  case Equals(dom) => Top(dom)
//  case CondEquals(cases) => ???
//  case MemberOf(\/-(SET)) => Top(\/-(A_SET))
//  case MemberOf(s) => Top(s)
////  case _ => ???
// }
 def domain(p: PSet): Ev[PSet] = p match {
  case dom: ConcPSet => Top(dom)
  case AMember(SET) => Top(A_SET)
  case AMember(s) => Top(s)
  case other =>
    println(other)
    ???
//  case _ => ???
 }
}