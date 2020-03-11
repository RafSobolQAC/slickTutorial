import sbt.Keys.libraryDependencies

name := "slickTutorial"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "3.0.0" % Test
libraryDependencies ++= Seq(
"com.typesafe.slick" %% "slick" % "3.3.2",
"org.slf4j" % "slf4j-nop" % "1.6.4",
"com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
"mysql" % "mysql-connector-java" % "6.0.6")


