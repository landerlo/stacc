class: middle

#Insert fancy front slide

---

class: top
#WHO AM I

...

---

#STACC is 

* Nobel core calculus for FP languages
  - Foundation to experiment new FP lang ideas

* Alternative interpretation
    - Not founded on the Lambda calculus

???

Takes the unorthodox approach of not being rooted on Lambda C

Is a core calculus to support the building of FP languages


---

#Why Stacc

* Wide conceptual gap between Lambda Calculus and advanced FP languages
    - Stacc sits in between narrowing gap

* Unifies many PL constructs in a minimalistic and approachable way
    - Familiar notation and semantics rooted on Set theory


???

Do we really need more type theories?

As an FP practitioner outside academia I've found this wide gap

---

#STACC: 

* Set Theoretic

* Algebraic

* Constructive

* Calculus

---

# Set theoretic

* Primary building block is the set of predicates

* Set semantics
    - e.g. Set union is _function application_

--

* <p><i><b>ST</b></i> also for Structural!</p>

---

# Algebraic

* LC has application and product
    - lambda x. xyz

* STACC has application, product and coproduct

* Coproduct: conditional values
```sh
    b = | 0  if a < 0
        | a  otherwise
```

???

Disjoint union

---

# Constructive

* Inspired by Intuitionistic Type Theory

* In order to have a proof we need to _*construct*_ it

* Evaluation is successful if it doesn't result in absurdity

* i.e. expression `2 < 1` is _impossible_ to construct

--

<p class="large red"> Blow up evaluation!</p>

???

Let's see graphical example of a constructive smaller than

LEGO time

Just as well your program should collapse.

STATIC TYPING typechecker's job this doesn't happen at runtime

---

#Properties vs Booleans

* Commonly logic and properties represented as booleans

    - Boolean blindness

* We want predicates enforceable by compiler

* Line between type and refinement or constraint blurred

--

* Propositions as types!

---

# Decidability

* Add-hoc constrains
    - i.e. not formally verified

* Type level enforcement of business invariants

* Push invariant checking to the caller
    - Function logic is simpler as domain is constrained

---

# Inversion of Optionality

* Mainstream approach: function handles optionality

* Principled

    - squareRoot: Double => Option[Double]

--

* The hidden surprise
    - squareRoot: Double => Double

--

* Non total functions abound
   - division:   Double => Double

---
class: middle
# Demand Totality

## We should push up optionality to the caller

* i.e squareRoot: NonNegativeDouble => NonNegativeDouble

* Usually achieved with newtypes
???

---

# Newtype shortcomings


* No support for intersection and conjuction of properties
    - e.g. with multiple invariants
```haskell
        newtype propA 
        newtype propB
```

* We cannot create a type intersection
    - It would require newtype `propA_and_propB`

???

There are some fringe ways

Dotty might be do things better

---

background-image: url(img/uncle-bob.png)

---
class:middle

## We are missing composability at the type level

---

#An example: safe factorial

`safeFactorial: x: Nat, x < 20 => Nat`

--

* We will have to build Naturals, smaller than (<) and addition

---

# Peano Numbers

* Zero is the empty set {}

* Succ is the successors of the previous number.
  - A successor is an object with variable _*pred*_ pointing to predecessor


```sh 
   zero =                   {} 
   one  =          { pred = zero }
   two  = { pred = one }
   two  = { pred = { pred = {} } } 
```

???

 * All the data structures we can create are finite

 ... But where are the types? We haven't created any abstraction about the Naturals.

---

# Nat Type

* All the data structures we have created are finite

* We want to specify `x ∈ Nat`

* Let's try:

```
   Nat = {} | Succ

   Succ = ???
```

---

# Inductive types

* References to create induction
    - References to structures in the tree gives us recursion

```
    Nat = {} | Succ
    Succ = { pred: Nat }
```

--

* Because we are only in the realm of data this is safe. 
    - _*NOT*_ general recursion
    - Gives us a solid foundation.

???

---

background-image: url(img/nats-graph-scaled.png)

---

#Succ in STACC

-
* Nat -> Nat *too powerful* for the induction step
    - It could be adding two or never terminate

* In Stacc Succ has fixed-point, data nature
    - Can only reference preexisting Nat
    - Data layering

* Well formed tree structure

--

* Maybe we need a signature for induction steps. Ideas welcome

---

# Creating our own constraint

* Let`s create smaller than `(<)`

```
    < = { a: Nat
          b: Nat

          => | {}               if  a  = {}, b /= {}  
             | {} ∈ {}          if  b  = {}          
             | a.pred < b.pred  if  b /= {}, a /= {}
    }
```
    - In the second clause we introduce Absurdity.

* All the paths in the disjunction must be disjoint, i.e. no overlap.
    - More principled than the usual fall through case statements

---
class:middle

##"Fast and loose __proving__ is morally correct"

???

  Paraphrasing the famous FP quote from bananas &  & barbed wire

---


# FUNCTIONS

* e.g. Addition
```sh
add = {
  a: Nat
  b: Nat

  => 
     | {},     if a = {}, b = {}
     | b,      if a = {}
     | a,      if b = {}
     | self u { 
          a = Succ u { pred = a}
          b = b.pred 
        },     if a /= {} & b /= {}
}
```

???


---

# Parametricity

* A variable has a membership constrain on another variable which is a Set

* No different semantics than other values
    - You can compare assign, copy, etc.

```sh
{

T ∈  Set
a ∈ T
}
```

---

#E.g. Identity

```sh
id = { 
    T ∈ Set
    x ∈ T 
     => x
}
```
--

* We apply the set property same as any other value

```sh
idNat = id u { T = Nat }
idNat = {
    T ∈ Set
    x ∈ T 
    T = Nat 
    => x
}
```

???

---
#Lists


---

# Thank you



* Demand ad-hoc constraints

--

* Fast and loose proving is morally correct

--

* Stack your blocks and build types


