import sbt.Keys._

name := "cucumber-test"

organization := "com.github.trod"

version := "0.0.2"
mainClass in (Compile, run) := Some("com.github.trod.company.support.Application")

val restAssuredV = "3.0.2"
val cucumberV = "1.2.4"
val scalatestV = "2.2.4"
val configV = "1.2.1"
val json4sV = "3.2.11"

lazy val main = project.in(file("."))
  .settings(scalaVersion := "2.11.8")
  .settings(test in assembly := {})
  .settings(mainClass in assembly := Some("com.github.trod.company.support.Application"))
  .settings(assemblyJarName := "qa-puzzle.jar")
  .settings(libraryDependencies ++= Seq(
    "info.cukes" % "cucumber-core" % cucumberV % "test",
    "info.cukes" %% "cucumber-scala" % cucumberV % "test",
    "info.cukes" % "cucumber-jvm" % cucumberV % "test",
    "info.cukes" % "cucumber-junit" % cucumberV % "test",
    "io.rest-assured" % "scala-support" % restAssuredV % "test",
    "com.typesafe" % "config" % configV % "test",
    "org.scalatest" %% "scalatest" % scalatestV % "test",
    "org.json4s" %% "json4s-jackson" % json4sV % "test"
  ))



enablePlugins(CucumberPlugin)

CucumberPlugin.glue := "com/github/trod/company"

testFrameworks += new TestFramework("com.waioeka.sbt.runner")