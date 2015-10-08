import com.tuplejump.sbt.yeoman.Yeoman

name := """blog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.7.play24",
  "org.reactivemongo" %% "reactivemongo-extensions-json" % "0.11.7.play24",
  "com.mohiva" %% "play-silhouette" % "3.0.0",
  "net.ceedubs" %% "ficus" % "1.1.2",
  "net.codingwell" %% "scala-guice" % "4.0.0",
   "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0",
  "com.mohiva" %% "play-silhouette-testkit" % "3.0.0" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test",
  specs2 % Test
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Atlassian Releases" at "https://maven.atlassian.com/public/",
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

Yeoman.yeomanSettings
