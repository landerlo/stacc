package stacc.logic

import stacc.ast.PSet

object SubsumptionRelation {

 trait SubsumptionRelation
 case class Subsumption(a: PSet, subsumedIn: PSet) extends SubsumptionRelation
 case class Orthogonal(a: PSet, b: PSet) extends SubsumptionRelation
 case class Intersection(resA: PSet, intersection: PSet, restB: PSet) extends SubsumptionRelation

 def relation(a: PSet, b: PSet): Ev[SubsumptionRelation] = {
  Orthogonal(a, b)
 }
}
