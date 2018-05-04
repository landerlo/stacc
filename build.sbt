name := "stacc"

scalaVersion in ThisBuild := "2.12.6"

scalacOptions in ThisBuild ++= Seq(
"-language:_",
"-Ypartial-unification",
"-Xfatal-warnings"
)

libraryDependencies ++= Seq(
    "default" %% "graph" % "0.1-SNAPSHOT",
    "com.lihaoyi" %% "fastparse" % "1.0.0",
    "org.scalaz" %% "scalaz-core" % "7.2.21",
    "com.github.mpilquist" %% "simulacrum" % "0.12.0",
    "com.chuusai" %% "shapeless" % "2.3.3",
    "org.scalacheck" %% "scalacheck" % "1.13.4",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")
addCompilerPlugin(
"org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
)
