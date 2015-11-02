name := """adapteach"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
	ws,
  "org.reactivemongo" %% "reactivemongo" % "0.11.7",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "io.netty" % "netty" % "3.9.9.Final" force()
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
