val commonSettings = Seq(
  organization := "io.github.shogowada.statictags",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  ivyScala := ivyScala.value.map {
    _.copy(overrideScalaVersion = true)
  }
)

lazy val statictags = crossProject
    .settings(commonSettings: _*)
    .settings(
      name := "core"
    )

lazy val jvm = statictags.jvm
lazy val js = statictags.js

lazy val generator = (project in file("generator"))
    .settings(commonSettings: _*)
    .settings(
      name += "generator",
      libraryDependencies ++= Seq(
        "com.google.inject" % "guice" % "4.1.0",
        "com.github.tototoshi" %% "scala-csv" % "1.3.3",

        "org.scalatest" %% "scalatest" % "3.0.0" % "test"
      )
    )
    .dependsOn(jvm)
