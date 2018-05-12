package nuevo

import org.scalatest.FreeSpec
import scalaz.{IList, INil}

class Test extends FreeSpec {

  "Try ILIt" in {

    val x = "a" :: "b" :: "c" :: "d" :: INil()

    println(x.intersperse("1"))



  }

}
