import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "quickquizz-demo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.twitter4j" % "twitter4j-core" % "(2.2,)"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += "twitter4j org" at "http://twitter4j.org/maven2"
    )


}
