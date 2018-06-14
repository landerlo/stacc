package stacc

import scalaz.{\/, \/-}
import stacc.ast._
import stacc.logic.{Ev, Top}

package object math {

 def domain(p: Prop): Ev[Ref \/ PSet] = p match {
  case Equals(dom) => Top(dom)
  case CondEquals(cases) => ???
  case MemberOf(\/-(SET)) => Top(\/-(A_SET))
  case MemberOf(s) => Top(s)
//  case _ => ???


 }
}