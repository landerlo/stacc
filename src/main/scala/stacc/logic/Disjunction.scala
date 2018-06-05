package stacc.logic

import stacc.ast.PSet

class Disjunction {

}


case class Disj(cases: Seq[Case])

case class Case(cond: PSet, value: PSet)



