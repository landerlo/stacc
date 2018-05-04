package graph

import org.scalatest.FreeSpec
class NodeSpec extends FreeSpec {

  trait Op[A]
  object Sum extends Op[Int]

  "typed node contains elements" - {

//    assert { n.addLeafs(4, 5) ===  Seq(4, 5) }
  }

  "Node contains Nodes with operation" - {

   // fromLeafs[Op[Int], Int](3, 2).addNodes(fromLeafs[Op[Int], Int](1, 1))


  }

}
