val commonSettings = Seq(
  organization := "io.github.shogowada.statictags",
  version := "0.1.0-SNAPSHOT",
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/shogowada/statictags")),
  scalaVersion := "2.11.8",
  ivyScala := ivyScala.value.map {
    _.copy(overrideScalaVersion = true)
  },
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    isSnapshot.value match {
      case true => Some("snapshots" at nexus + "content/repositories/snapshots")
      case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
    }
  }
)

lazy val root = project
    .aggregate(jvm, js, generator)
    .settings(commonSettings: _*)
    .settings(
      publishArtifact := false
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
      ),
      publishArtifact := false
    )
    .dependsOn(jvm)
