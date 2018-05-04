package graph

trait Node[P[_], C] {
  def addLeafs(ls: C*): Node[P, C]
  def addNodes(ns: P[C]*): Node[P, C]
  def nodes: Vector[P[C]]
  def leafs: Vector[C]
}

object Node {

  case class CNode[P[C], C](nodes: Vector[P[C]], leafs: Vector[C]) extends Node[P, C] {
    def addLeafs(ls: C*):    Node[P, C] = CNode(nodes, leafs ++ ls.toVector)
    def addNodes(ns: P[C]*): Node[P, C] = CNode(nodes ++ ns.toVector, leafs)
  }

  def fromLeafs[P[_], C](leafs: C*): Node[P, C] = CNode(Vector(), leafs.toVector)
  def fromNodes[P[_], C](nodes: P[C]*): Node[P, C] = CNode(nodes.toVector, Vector())

}
