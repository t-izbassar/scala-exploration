ThisBuild / scalaVersion := "2.13.1"
ThisBuild / organization := "com.github.tizbassar"

lazy val hello = (project in file("."))
  .settings(
    name := "Scala Exploration"
  )