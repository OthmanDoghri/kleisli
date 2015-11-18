lazy val root = (project in file(".")).
  settings(
    name := "Kleisli",
    version := "1.0",
    scalaVersion := "2.11.7",
    sbtPlugin := true,
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.4"  
  )