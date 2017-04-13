publishTo := {
  val nexus = "https://oss.sonatype.org/"
  isSnapshot.value match {
    case true => Some("snapshots" at nexus + "content/repositories/snapshots")
    case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}
publishArtifact := false

crossScalaVersions := Seq("2.11.8", "2.12.1")

val commonSettings = Seq(
  organization := "io.github.shogowada",
  name := "statictags",
  version := "2.5.0",
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/shogowada/statictags")),
  scalaVersion := "2.12.1",
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

lazy val root = project
    .aggregate(jvm, js)
    .settings(commonSettings: _*)
    .settings(
      publishArtifact := false
    )

lazy val statictags = (crossProject in file("statictags"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "org.scalatest" %%% "scalatest" % "3.0.0" % "test"
      )
    )

lazy val jvm = statictags.jvm
lazy val js = statictags.js
