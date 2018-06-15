package stacc.util

import scalaz.\/
import stacc.ast.{Equals, PSet, Prop, Ref}
import stacc.logic.{Ev, Top}

object TestHelpers {

 val mockResolve: Ref \/ PSet => Ev[Prop[PSet]] =
  _.fold(_ => throw new UnsupportedOperationException("No resolving needed"), x => Top(Equals(x)))
}