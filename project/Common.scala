import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.routes.RoutesKeys.routesGenerator
import sbt.Keys._
import sbt._

object Common {

  val settings: Seq[Setting[_]] = Seq(
    organization := "org.krenzaslv",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.11.8",
    name := """blog"""
  )


  val playSettings = settings ++ Seq(
    routesGenerator := InjectedRoutesGenerator,
    libraryDependencies ++= Seq(
      "org.reactivemongo" %% "play2-reactivemongo" % "0.12.0",
      "org.reactivemongo" %% "reactivemongo-extensions-json" % "0.11.7.play24",
      "com.mohiva" %% "play-silhouette" % "4.0.0",
      "net.codingwell" %% "scala-guice" % "4.0.0",
      "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0",

      "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
      // specs2 % Test
    ),
    resolvers ++= Seq(
      "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
      "Atlassian Releases" at "https://maven.atlassian.com/public/",
      "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
    )
  )
}