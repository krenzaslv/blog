lazy val common = (project in file("common")).enablePlugins(PlayScala)

lazy val web = (project in file("web")).enablePlugins(PlayScala).aggregate(common).dependsOn(common % "test->test;compile->compile")

lazy val admin = (project in file("admin")).enablePlugins(PlayScala).aggregate(common).dependsOn(common % "test->test;compile->compile")

lazy val root = (project in file(".")).
  enablePlugins(PlayScala).
  aggregate(web, admin).
  dependsOn(web, admin)

Common.settings

coverageExcludedPackages := """<empty>;router\\..*;Reverse.*;router.Routes.*;"""