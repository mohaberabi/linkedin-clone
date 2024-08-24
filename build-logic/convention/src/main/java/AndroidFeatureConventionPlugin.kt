import com.android.build.api.dsl.LibraryExtension
import convention.addFeatureDips
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import convention.configureKotlinAndroid
import org.gradle.kotlin.dsl.configure

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("linkedinclone.android.library")
//                apply("androidx.navigation.safeargs.kotlin")

            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildFeatures {
                    viewBinding = true
                }
            }

            dependencies {
                addFeatureDips(target)
                "implementation"(project(":core:presentaton:ui"))
                "implementation"(project(":core:presentaton:design_system"))
            }
        }
    }
}
