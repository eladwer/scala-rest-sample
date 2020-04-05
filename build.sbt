name := "scala-rest-sample"

version := "0.1"

scalaVersion := "2.12.7"
val PotgresqlVersion = "42.2.2"
val HikariVersion = "3.1.0"
val CirceVersion = "0.9.3"
val Http4sVersion = "0.18.11"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-dsl"          % Http4sVersion,
  "org.http4s" %% "http4s-circe"        % Http4sVersion,
  "io.circe"   %% "circe-generic"       % CirceVersion,

  "org.postgresql" % "postgresql" % PotgresqlVersion,
  "com.zaxxer"     % "HikariCP"   % HikariVersion
)