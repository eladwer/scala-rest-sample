name := "scala-rest-sample"

version := "0.1"

scalaVersion := "2.12.7"
val PotgresqlVersion = "42.2.2"
val HikariVersion = "3.1.0"
val CirceVersion = "0.9.3"
val http4sVersion = "0.21.2"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe"        % http4sVersion,
  "io.circe" %% "circe-generic" % "0.13.0",
  
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.13.0",
  "org.postgresql" % "postgresql" % PotgresqlVersion,
  "com.zaxxer"     % "HikariCP"   % HikariVersion
)

scalacOptions ++= Seq("-Ypartial-unification")


// No need to run tests while building jar
test in assembly := {}

mainClass in assembly := Some("com.elad.rest.MyServer")

// Simple and constant jar name
assemblyJarName in assembly := s"app-assembly.jar"
// Merge strategy for assembling conflicts
assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case _ => MergeStrategy.first
}

