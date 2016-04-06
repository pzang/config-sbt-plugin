sbtPlugin := true

//Change to your organization
organization := "me.pzang"

//Change to your plugin name
name := """scala-config-sbt-plugin"""

//Change to the version
version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-feature")

resolvers += Resolver.sonatypeRepo("snapshots")

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)


// Scripted - sbt plugin tests
scriptedSettings

scriptedLaunchOpts += "-Dproject.version=" + version.value
