import play.sbt.PlayImport._
import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

object Dependencies extends AutoPlugin {

  override def trigger = allRequirements
  override def requires: sbt.Plugins = JvmPlugin

  override def projectSettings = Seq(
    libraryDependencies ++= Seq(
      guice,
      ws,
      caffeine,
      "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
      "io.lemonlabs" %% "scala-uri" % "3.2.0",
      "net.codingwell" %% "scala-guice" % "5.0.0",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )
}
