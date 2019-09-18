name := "bigdata-challenge"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.25",
  "com.typesafe.akka" %% "akka-http"   % "10.1.9",
  "de.heikoseeberger"               %% "akka-http-json4s"  % "1.28.0",
  "org.json4s"                      %% "json4s-native"     % "3.6.7",
  "org.json4s"                      %% "json4s-jackson"    % "3.6.7",
  "org.json4s"                      %% "json4s-ext"        % "3.6.7",
  "com.typesafe.scala-logging"      %% "scala-logging"     % "3.9.2",
  "ch.qos.logback"                  %  "logback-classic"   % "1.2.3",
  "io.spray"                        %%  "spray-json"        % "1.3.5",
  "com.typesafe.akka"               %% "akka-http-spray-json" % "10.1.9"
)