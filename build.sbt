import com.typesafe.config.{Config, ConfigFactory}

// Project configurations using scala classes at /project folder.
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(ConvertUnitService, Dependencies)

scalaVersion := "2.13.5"

lazy val conf: Config = ConfigFactory.parseFile(new File("conf/application.conf"))

// Docker Configuration
import com.typesafe.sbt.packager.docker.{DockerChmodType, DockerPermissionStrategy}
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown

Docker / maintainer := "felipebonezi@gmail.com"
Docker / packageName := conf.getString("play.app.name")
Docker / version := conf.getString("play.app.version")
dockerExposedPorts := Seq(9000)
dockerUpdateLatest := true