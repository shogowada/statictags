publishTo := {
  val nexus = "https://oss.sonatype.org/"
  isSnapshot.value match {
    case true => Some("snapshots" at nexus + "content/repositories/snapshots")
    case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}
publishArtifact := false

val commonSettings = Seq(
  organization := "io.github.shogowada",
  name := "statictags",
  version := "0.2.0",
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/shogowada/statictags")),
  scalaVersion := "2.11.8",
  ivyScala := ivyScala.value.map {
    _.copy(overrideScalaVersion = true)
  },
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  ),
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

lazy val jvm = statictags.jvm
lazy val js = statictags.js

lazy val generator = (project in file("generator"))
    .settings(commonSettings: _*)
    .settings(
      name += "-generator",
      libraryDependencies ++= Seq(
        "com.google.inject" % "guice" % "4.1.0",
        "com.github.tototoshi" %% "scala-csv" % "1.3.3"
      ),
      publishArtifact := false
    )
    .dependsOn(jvm)
