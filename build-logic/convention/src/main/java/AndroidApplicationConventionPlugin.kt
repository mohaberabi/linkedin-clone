import com.android.build.api.dsl.ApplicationExtension
import convention.ExtensionType
import convention.addCoreLibs
import convention.addFeatureDips
import convention.configureBuildTypes
import convention.configureKotlinAndroid
import convention.libs


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.gms.google-services")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }
                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.APPLICATION)

            }
            dependencies {
                addCoreLibs(target)
                addFeatureDips(target)
            }

        }
    }
}