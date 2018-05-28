package stacc.util

import org.scalatest.FreeSpec
import scalaz.NonEmptyList

class NELExtensionTest extends FreeSpec {
  "distinct just with Equiv" in {
    assert {
      NELExtension.distinct(NonEmptyList.nels(1, 3, 4, 1)) === NonEmptyList.nels(1, 3, 4) }
  }

}
