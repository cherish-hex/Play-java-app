name := """play-java-intro"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  // https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
  "org.mongodb" % "mongo-java-driver" % "3.3.0",
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
  "com.fasterxml.jackson.core" % "jackson-core" % "2.8.1",
   javaWs

)



//fork in run := true