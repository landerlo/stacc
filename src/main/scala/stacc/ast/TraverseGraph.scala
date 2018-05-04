package stacc.ast

object TraverseGraph extends  App {

  def traverseValues(root: ConcreteVarPSet)(f: PSet => PSet): ConcreteVarPSet = {
    println(s"TR $root")

    def step(e: PSet): PSet =
    e match {
      case ref: Ref => ref
      case x: ConcreteVarPSet => ConcreteVarPSet(x.vs.map { case PropOnVar(v, p) => PropOnVar(v, step(p)) })
    }
    ConcreteVarPSet(root.vs.map { case PropOnVar(v, p) => PropOnVar(v, step(p)) })
  }

  def fold[B](root: PSet)(zero: B)(f: (B, PSet) => B): B = {
     def step(b: B, e: PSet): B = {
       println(s"Fold $b $e")
       e match {
         case ref: Ref => b
         case x: ConcreteVarPSet => x.vs.foldLeft(b) { case (b, PropOnVar(v, p)) => step(f(b, p), p) }
         case p => step(f(b, p), p)
       }
     }
    step(f(zero, root), root)
  }
}
