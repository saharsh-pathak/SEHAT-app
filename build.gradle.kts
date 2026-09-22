plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
}

val localAppData: String = System.getenv("LOCALAPPDATA") ?: System.getProperty("user.home")
val externalBuildDir = File(localAppData, "AndroidBuilds/SEHAT")

allprojects {
    layout.buildDirectory.set(File(externalBuildDir, if (project == rootProject) "root" else project.name))
}