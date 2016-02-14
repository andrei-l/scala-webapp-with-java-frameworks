import sbt.Keys._

val springBootStarterVersion = "1.3.1.RELEASE"
val hibernateVersion = "5.1.0.Final"
val jacksonVersion = "2.6.3"

val jacksonScalaDependency = "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
val scalaJava8Dependency = "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0"


val scalaTestDependency: sbt.ModuleID = "org.scalatest" %% "scalatest" % "2.2.4" % Test
val mockitoDependency: sbt.ModuleID = "org.mockito" % "mockito-core" % "1.10.19" % Test
val jsonAssertDependency: sbt.ModuleID = "org.skyscreamer" % "jsonassert" % "1.2.3" % Test
val junitInterfaceDependency: sbt.ModuleID = "com.novocode" % "junit-interface" % "0.11" % Test
val springTestDBUnitDependency: sbt.ModuleID = "com.github.springtestdbunit" % "spring-test-dbunit" % "1.2.1" % Test
val dbUnitDependency: sbt.ModuleID = "org.dbunit" % "dbunit" % "2.5.1" % Test
val springBootStarterTestDependency = "org.springframework.boot" % "spring-boot-starter-test" % springBootStarterVersion % Test

val hibernateDependency: sbt.ModuleID = "org.hibernate" % "hibernate-entitymanager" % hibernateVersion
val hibernateJava8Dependency: sbt.ModuleID = "org.hibernate" % "hibernate-java8" % hibernateVersion

val springBootStarterDependency = ("org.springframework.boot" % "spring-boot-starter" % springBootStarterVersion)
  .exclude("org.springframework.boot", "spring-boot-starter-logging")
val springBootStarterWebDependency = ("org.springframework.boot" % "spring-boot-starter-web" % springBootStarterVersion)
  .exclude(springBootStarterDependency.organization, springBootStarterDependency.name)
val springBootStarterDataJPADependency = ("org.springframework.boot" % "spring-boot-starter-data-jpa" % springBootStarterVersion)
  .exclude(hibernateDependency.organization, hibernateDependency.name)
  .exclude(springBootStarterDependency.organization, springBootStarterDependency.name)

lazy val commonSettings = Seq(
  scalaVersion := "2.11.7",
  crossPaths := false,
  publishMavenStyle := true,
  organization := "com.github.swwjf"
)

lazy val swwjf = (project in file("."))
  .aggregate(libs)
  .aggregate(libsJPA)
  .settings(commonSettings: _*)
  .settings(Seq(
    name := "swwjf",
    version := "0.1.0",
    parallelExecution := false,
    sourcesInBase := false
  ))

lazy val webservices = (project in file("webservices"))
  .settings(commonSettings: _*)
  .settings(Seq(
    exportJars := true,
    name := "webservices",
    version := "0.1.0",
    sourcesInBase := true,
    parallelExecution := false,
    libraryDependencies ++= Seq(
      /* Spring Dependencies */
      springBootStarterWebDependency,
      springBootStarterDependency,

      /* Misc Dependencies */
      scalaJava8Dependency,
      jacksonScalaDependency,
      "ch.qos.logback" % "logback-classic" % "1.1.3",

      /* Test Dependencies */
      scalaTestDependency,
      mockitoDependency,
      junitInterfaceDependency,
      jsonAssertDependency,
      dbUnitDependency,
      springTestDBUnitDependency,
      springBootStarterTestDependency
    )
  ))
  .dependsOn(libsJPA)

lazy val libs = (project in file("libs"))
  .aggregate(libsJPA)
  .settings(commonSettings: _*)
  .settings(Seq(
    name := "libs",
    version := "0.1.0",
    sourcesInBase := false
  ))

lazy val libsJPA = (project in file("libs/libs-jpa"))
  .settings(commonSettings: _*)
  .settings(Seq(
    name := "libs-jpa",
    version := "0.1.0",
    sourcesInBase := true,
    exportJars := true,
    libraryDependencies ++= Seq(
      /* Spring Dependencies */
      springBootStarterDataJPADependency,
      springBootStarterDependency % Provided,
      hibernateDependency.exclude("org.apache.geronimo.specs", "geronimo-jta_1.1_spec"),
      hibernateJava8Dependency,

      /* Misc Dependencies */
      "org.apache.commons" % "commons-dbcp2" % "2.1.1",
      "com.h2database" % "h2" % "1.4.190"
    )
  ))
