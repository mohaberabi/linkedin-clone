package convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope


internal fun DependencyHandlerScope.addFeatureDips(
    project: Project,
) {
    addCoreLibs(project)
    val libs = project.libs
    add("implementation", libs.findLibrary("lifecycleViewModel").get())
    add("implementation", libs.findLibrary("lifecycleRuntime").get())
    add("implementation", libs.findLibrary("androidxFragment").get())

}


