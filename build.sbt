name := "jpnsrv"

version := "0.0.1"

organization := "com.jasonnerothin"

scalaVersion := "2.9.2"

resolvers ++= Seq(
                    "snapshots"   at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"    at "http://oss.sonatype.org/content/repositories/releases",
                    "central"     at "http://repo1.maven.org/maven2"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")

retrieveManaged := true

port in container.Configuration := 6789

libraryDependencies ++= {
  val liftVersion = "2.5-M2"
  Seq(
    "ch.qos.logback"    % "logback-classic"         % "1.0.6"                   % "compile",
    "com.fasterxml"     % "jackson-module-scala"    % "1.9.3"                   % "compile",
    "com.h2database"    % "h2"                      % "1.3.167"                 % "test",
    "junit"             % "junit"                   % "4.8.2"                   % "test",
    "net.liftweb"       %% "lift-webkit"            % liftVersion               % "compile",
    "net.liftweb"       %% "lift-mapper"            % liftVersion               % "compile",
    "net.liftmodules"   %% "lift-jquery-module"     % (liftVersion + "-1.0")    % "compile",
    "org.eclipse.jetty" % "jetty-webapp"            % "8.1.7.v20120910"         % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet"     % "3.0.0.v201112011016"     % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "org.mockito"       % "mockito-all"             % "1.9.0"                   % "test",
    "org.mongodb"       % "casbah-core_2.9.2"       % "2.4.1"                   % "compile",
    "org.mongodb"       % "casbah-query_2.9.2"      % "2.4.1"                   % "compile",
    "org.scalatest"     % "scalatest_2.9.2"         % "1.8"                     % "test",
    "org.specs2"        %% "specs2"                 % "1.12.1"                  % "test"
  )
}

