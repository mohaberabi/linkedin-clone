package convention


import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.VersionCatalogsExtension

fun DependencyHandler.addCoreLibs(project: Project) {
    val libs = project.libs
    add("implementation", libs.findLibrary("androidx-core-ktx").get())
    add("implementation", libs.findLibrary("androidx-appcompat").get())
    add("implementation", libs.findLibrary("material").get())
    add("implementation", libs.findLibrary("androidx-constraintlayout").get())
    add("implementation", libs.findLibrary("coil").get())

}
