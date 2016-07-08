name := "SlickSourceGenerator4Postgresql"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"