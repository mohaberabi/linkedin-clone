package convention


import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addHiltLibs(project: Project) {
    add("implementation", project.libs.findLibrary("hilt").get())
    add("ksp", project.libs.findLibrary("hiltCompiler").get())
}
