publishTo := {
  val nexus = "https://oss.sonatype.org/"
  isSnapshot.value match {
    case true => Some("snapshots" at nexus + "content/repositories/snapshots")
    case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}
publishArtifact := false

crossScalaVersions := Seq("2.11.8", "2.12.0")

val commonSettings = Seq(
  organization := "io.github.shogowada",
  name := "statictags",
  version := "1.1.0-SNAPSHOT",
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/shogowada/statictags")),
  scalaVersion := "2.12.0",
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
    .aggregate(jvm, js, generator)
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

lazy val generator = (project in file("generator"))
    .settings(commonSettings: _*)
    .settings(
      name += "-generator",
      libraryDependencies ++= Seq(
        "com.google.inject" % "guice" % "4.1.0",
        "org.apache.commons" % "commons-csv" % "1.3",

        "org.scalatest" %% "scalatest" % "3.0.0" % "test"
      ),
      publishArtifact := false,
      (javaOptions in run) ++= Seq(s"-Dbase.directory=${(baseDirectory in jvm).value / ".." / "shared"}"),
      (fork in run) := true
    )
    .dependsOn(jvm)
