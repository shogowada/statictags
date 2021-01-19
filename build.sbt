
import sbtcrossproject.CrossProject

val commonSettings = Seq(
  organization := "io.github.shogowada",
  name := "statictags",
  version := "2.6.0-SNAPSHOT",
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/shogowada/statictags")),

  crossScalaVersions := Seq("2.11.12", "2.12.1", "2.13.1"),
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq(
    "-deprecation", "-unchecked", "-feature", "-Xcheckinit", "-target:jvm-1.8", "-Xfatal-warnings"
  ),

  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    isSnapshot.value match {
      case true => Some("snapshots" at nexus + "content/repositories/snapshots")
      case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
    }
  },
  pomExtra := <scm>
    <url>git@github.com:shogowada/statictags.git</url>
    <connection>scm:git:git@github.com:shogowada/statictags.git</connection>
  </scm>
      <developers>
        <developer>
          <id>shogowada</id>
          <name>Shogo Wada</name>
          <url>https://github.com/shogowada</url>
        </developer>
      </developers>
)

lazy val root = (project in file("."))
    .settings(commonSettings: _*)
    .settings(
      crossScalaVersions := Nil, //must be set to Nil on the aggregating project
      skip in publish := true,
      publish := ((): Unit),
      publishLocal := ((): Unit),
      publishM2 := ((): Unit)
    )
    .aggregate(jvm, js)

lazy val statictags = CrossProject("statictags", file("statictags"))(JSPlatform, JVMPlatform)
    .crossType(sbtcrossproject.CrossType.Pure)
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "org.scalatest" %%% "scalatest" % "3.2.2" % "test"
      )
    )

lazy val jvm = statictags.jvm
lazy val js = statictags.js
