name := """play-java-intro"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  // If you enable PlayEbean plugin you must remove these
  // JPA dependencies to avoid conflicts.
//  javaJpa,
//  "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final",
  // https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
  "org.mongodb" % "mongo-java-driver" % "3.3.0",
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
  "com.fasterxml.jackson.core" % "jackson-core" % "2.8.1"

)



//fork in run := true