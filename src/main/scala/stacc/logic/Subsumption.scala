package stacc.logic

import stacc.ast.PSet

object SubsumptionRelation {

 trait SubsumptionRelation
 case class Subsumption(a: PSet, subsumedIn: PSet) extends SubsumptionRelation
 case class Orthogonal(a: PSet, b: PSet) extends SubsumptionRelation

 def relation(a: PSet, b: PSet): SubsumptionRelation = {
  Orthogonal(a, b)
 }
}
