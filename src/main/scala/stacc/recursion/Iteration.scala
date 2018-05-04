package stacc.recursion

object Iteration {

 def iterateUntilStable[A](a: A)(f: A => A): A = {
  val fa = f(a)
  if (a == fa) a else iterateUntilStable(fa)(f)
 }

}
