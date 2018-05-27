package stacc

import scalaz.\/
import stacc.ast.{Equals, PSet, Prop, Ref}
import stacc.logic.{Ev, Top}

package object math {

 def domain(p: Prop): Ev[Ref \/ PSet] = p match {
  case Equals(dom) => Top(dom)
  case _ => ???

 }
}