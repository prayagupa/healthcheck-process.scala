name := "PipelineWatch"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.6"

mainClass in Compile := Some("com.pipeline.watch.PipelineWatch")
