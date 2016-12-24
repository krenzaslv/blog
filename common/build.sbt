Common.playSettings

name := """blog-common"""

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

coverageExcludedPackages := """<empty>;router\\..*;Reverse.*;router.Routes.*;"""