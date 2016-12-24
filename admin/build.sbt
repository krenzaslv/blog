Common.playSettings

name := """blog-admin"""
updateOptions := updateOptions.value.withCachedResolution(true)

coverageExcludedPackages := """<empty>;router\\..*;Reverse.*;admin.Routes.*;"""