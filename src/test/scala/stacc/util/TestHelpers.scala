package stacc.util

import scalaz.\/
import stacc.ast.{PSet, Ref}
import stacc.logic.{Ev, Top}

object TestHelpers {

 val mockResolve: PSet => Ev[PSet] = (p: PSet) => p match {
  case _: Ref => throw new UnsupportedOperationException("No resolving needed")
  case x: PSet => Top(x)
 }
}