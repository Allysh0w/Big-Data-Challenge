import com.typesafe.sbt.packager.docker._

name := "bigdata-challenge"

version := "0.1"

scalaVersion := "2.12.8"

mainClass in Compile := Some("com.bigdata.challenge.Main")

dockerBaseImage := "adoptopenjdk/openjdk8:latest"

packageName in Docker := "javac7/big-data-challenge"
maintainer in Docker := "javac7"
packageSummary := "Big Data Challenge"
packageDescription := "Big Data Challenge"

publishArtifact in Test := true


enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.7",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7",
  "com.typesafe.akka"               %% "akka-stream"       % "2.5.25",
  "com.typesafe.akka"               %% "akka-http"         % "10.1.9",
  "de.heikoseeberger"               %% "akka-http-json4s"  % "1.28.0",
  "org.json4s"                      %% "json4s-native"     % "3.5.3",
  "org.json4s"                      %% "json4s-jackson"    % "3.5.3",
  "org.json4s"                      %% "json4s-ext"        % "3.5.3",
  "com.typesafe.scala-logging"      %% "scala-logging"     % "3.9.2",
  "ch.qos.logback"                  %  "logback-classic"   % "1.2.3",
  "io.spray"                        %%  "spray-json"        % "1.3.5",
  "com.typesafe.akka"               %% "akka-http-spray-json" % "10.1.9",
  "com.github.vickumar1981"         %% "stringdistance"       % "1.1.2",
  "org.apache.spark"                %  "spark-sql_2.12"    % "2.4.4"exclude("org.slf4j", "slf4j-log4j12"),
  "org.apache.spark"                %  "spark-mllib_2.12"  % "2.4.4"exclude("org.slf4j", "slf4j-log4j12"),
  "org.scala-lang"                  %   "scala-library"       % "2.12.8",
  "org.scala-lang.modules"           %% "scala-xml"         % "1.2.0",
  "org.tpolecat" %% "doobie-core"      % "0.7.0",
  "org.tpolecat" %% "doobie-postgres"  % "0.7.0",
  "org.scalatest" % "scalatest_2.12" % "3.0.8" % "test"


)
dependencyOverrides ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7"
)



dockerCommands ++= Seq(
  Cmd("USER", "root"),
  // setting the run script executable
  Cmd("RUN",
    "apt-get update"
  ),
  Cmd("RUN",
    "apt-get install -y curl"
  ),
  Cmd("RUN",
    "curl -L -o sbt-1.3.0.deb https://dl.bintray.com/sbt/debian/sbt-1.3.0.deb"
  ),
  Cmd("RUN",
    "dpkg -i sbt-1.3.0.deb"
  ),
  Cmd("RUN",
    "rm sbt-1.3.0.deb"
  ),
  Cmd("RUN",
    "apt-get update"
  ),
  Cmd("RUN",
    "apt-get install -y sbt git"
  ),
  Cmd("RUN",
    "apt-get clean"
  ),
  Cmd("RUN",
    "apt-get autoclean"
  )
)
  // setting a daemon user

