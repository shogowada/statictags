lazy val statictags = crossProject
    .settings(
      name := "statictags",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := "2.11.8",
      ivyScala := ivyScala.value.map {
        _.copy(overrideScalaVersion = true)
      }
    )
