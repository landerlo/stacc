---
layout: true
class: middle
---
<!--- ----------------------------------------------------- -->
<!---  01-whoami.org   -->

# WHO AM I

    - caveat emptor
    - All my academic publications have been in 140 char format
    - Low in the retweet index for the community <!-- comment?  --> 

???

    Main motivation for my interest in PL design are the frustrations from working with multiple mainstream languages.
    WIth a low

---

<!--- ----------------------------------------------------- -->
<!---  02.stack is   -->

# STACC is 

    * Nobel core calculus for FP languages
        - Intended as vehicle for researching my FP lang ideas
      
    * Unorthodox: not founded on the Lambda calculus
      - The foundational metatheory is richer than LC
      - Nevertheless the semantics can be encoded in LC
    
    * Aims at encoding advanced dependant FP constructs in a simple and unified way

---

# STACC:
    - Set Theoretic
    - Algebraic
    - Constructive
    - Calculus

---

* Set theoretic
    * Primary building block is the set of predicates 
    * Set semantics, union is function application
--
    * But the _*ST*_ could be just as well Structural

---

* Algebraic
    * LC has application and product
    * STACC has application, product and coproduct

---

* Constructive
   
    - In order to have a witness or a proof we need to construct it
    - In Stacc any construction that doesn't result in absurdity, aka Bottom, is considered a success.
    - Without having created any types, bottom can be summoned with {} e {}


      fistTen = x e Nat
                => | {},      if x  = 1  
                   | {} e {}, if x /= 1

???


    
TODO: Read COC

<!--- ----------------------------------------------------- -->
<!---  03-Motivation.org  -->

# Motivation
    * Start from a richer metatheory
    
    * Decidability

    * Hierarchical 

???

These points can be shown with the photos of sets in Math and

---

# Decidability vs formally verified

   * Add-hoc constrains
    - Push up the constraint
    - Complexity gets trimmed the more tight the function domain is. 

   * Type level enforcement of business invariants

???

Functions returning filtered results, Maybes or validation errors have the complexity of that optionality handling inside the function
and then of handling the result that needs to encode the required invariants.

We all understand the benefits of constraining the domain of a function at the type level and constraining the number of possible implementations 
through parametricity.

Pushing this further we need to demand that other invariants not expressible at the "type level" are also enforced through decidability.

very important for business invariants

---

* Intersection / conjunction

* "Fast and loose proving is morally correct"
    * The way to encode adhoc properties on elements
        e.g. newtype in haskell

        newtype propA 
        newttpe propB

* They cannot combine the with a fundamental connective like And or intersection.
    - It would require newtype `propA_and_propB`
---

* Uncle Bob: where is your composability now?    

---


* We are missing composability at the type level

---

<!--- ----------------------------------------------------- -->
<!---  04-grammar.org  -->


* Grammar

<!--  Mayba have side by side panels to show examples alongside grammar, but without distracting about alignment -->
```
VAR= id    --  a
REF= path  -- a.b.y

POV=   VAR PRED         -- a = {}
PRED=  e PSET              a = b
      |eq COND PSET        b e A
                           |{}, b  = {} 
                           |a   b  = {}

COND= 'always' | PSET


PSET= {}
     |POV* -
     |PSET U PSET   -- {} u {}
     |REF
```

--- 

# Examples

???

 * Show nested sets and the references to subelements in the tree.

 * At some point make parallelism with "well formed trees" / or image by V Voedich. Probably this should come earlier?

  Finish with remark that no interesting data can be represented with the deterministic approach. We need inductive types

---
```
Forall v, p in pset p e U

x e PWSet Y iff Forall v in VARS(Y), 
            v e VARS(X) AND Unified(x.v) PROP_SUBSET Unified(Y.v)

PROPS_SUBSET pa, pb = (pa, pb) match {
    case e A, e B:  A subset B
    case e A, e B:  A subset B
```

??? 
Introduce other rules

---



--- 

???

Well formed trees?
<!--- ----------------------------------------------------- -->
<!---  05-recursive.org  -->

# Inductive types
    
 * The most famous inductive types Naturals, in the Peano encoding
  - Let's consider zero is the starting point, let's use the empty set {}
  - We construct the successors of the previous number.
  - One as the successor of 0. 

```sh
   zero = {} 
   one = { pred = zero }
   two = { pred = one }
   two = { pred = { pred = {} } } 
```

---

 * All the data structures we can create are finite

 ... But where are the types? We haven't created any abstraction about the Naturals.
  We can just construct instances of numbers.

    
   We want x e Nat
   Nat = ???

   Induct one step:
   Nat = {} | Succ

   Succ = ???

   We miss the ability to refer to the recursion. i.e.

---

 * Enter references
   
    Being able to reference structures in the tree gives us cycle, loops, recursion.
```    
    Nat = {} | Succ
    Succ = { pred: Nat }
```
  * /IMG Graph of NAT with the loops
   
  * Because we are only in the realm of data this is safe. Not general recursion   
  
   Gives us a solid foundation.

???
  Is this codata?

---

* Succ: Nat -> Nat 
    * Is too powerful for the induction step Succ { pred: Nat }
    
    * f: Nat -> Nat can be adding two or not terminate in your lifetime your if f = 'the Nth Prime'

* In Stacc Succ { pred: Succ } can be seen as Nat -> Nat if necessary but it is explicitly typed as itself: 
    { pred: Nat }
    We create something depending on a Nat.

    * Maybe we need a signature for induction steps. Ideas welcome

---

<!--- ----------------------------------------------------- -->
<!---  06-typing  -->

# TYPING

* After seeing inductive types we'll explore the typing judgement

* A correct program in Stacc is a set of predicates that: 
    * don't lead to absurdity,  i.e. are true
    * They are internally consistent
  
* Rather than having a true of false in Stacc we have the ability to bring absurdity, bottom.
    { a e {} } member of the empty set, this is impossible.

---

*  There are builtins for the canonical equality that will bring absurdity, i.e. compilation error

   But we can create our own:

* Let's create smaller than *<*  being Nat
```
    < = { a: Nat,
          b: Nat,
          result = | {},              _if_  a  = {}, b /= {}  // (1) Indeed a < b the returned value is indiferent.
                   | {} e {},         _if_  b  = {}           // Absurdity a < b.
                   | a.pred < b.pred, _if_  b /= {}, a /= {}  // We don't know yet we retest with the predecessors
    }
```
    - In the first clause we introduce Absurdity.

* All the paths in the disjunction must be disjoint, i.e. no overlap.

---

* This way we can create a refinement type  < 2

    smallerThan2 = < u { b = { pred = { pred = {} }} }
             //  = _ < 2 
    We partially apply < with 2 on the upper bound

* We can use that:
    fact = {
        a: Nat # This is redundant once we refine
        a: < u { b = 20}
        ...
    }

    
    { y : Nat
      f = fact u { a = y } 
      /// !!! will fail. No evidence that f is smaller than that.

      # We need to provide the witness
      f = | fact u { a = y },  if a < u { b = 20 }   
    

    
* We 


<!--- ----------------------------------------------------- -->
<!---  07-functions.org  -->


* FUNCTIONS

* e.g. Addition. Relation of a product of two nats with another nat

Product of two nats 
{ a: Nat, b: Nat }

# Explain the typing here? Probably better before. After the recursive type.

Relation with the addition

{ a: Nat
  b: Nat

  result: Nat
  result = | {},     if a = {}, b = {}
           | b,      if a = {}
           | a,      if b = {}
           | self u { 
                 a = Succ u { pred = a}
                 b = b.pred 
             }       if a /= {} & b /= {}
}

---

???

/IMG of the relation between numbers sets

 show 1 + 2 


<!--- ----------------------------------------------------- -->
<!---  08-parametricity.org  -->

# Parametricity

* A variable has a membership constrain on another variable which is a Set
    - This value encodes the type parameter or type variable.

* No different semantics that the other values, you can compare assign, copy, etc.

* Not different from any other constraints or refinements

```sh
{

T e Set

a e T

}
```

---

#E.g. Identity

```sh
id = { 

    T e Set

    x e T 
    
     => x
}
```

---

* As expected we apply the set property same as any other value

idNat = id u { T = Nat }

???

Bonus material:

* Parameterised on the type is just a constraint

There is nothing special about the constraint that it needs to belong to that type.
Equally we could establish other properties.

smallerThan3 = {

x e ( < 3) 

}



<!--- ----------------------------------------------------- -->
<!---  09-dinamyctiping.org  -->


* Dinamyc typing
