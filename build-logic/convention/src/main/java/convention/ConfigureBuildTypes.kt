package convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

typealias DefaultExtension = CommonExtension<*, *, *, *, *, *>

internal fun Project.configureBuildTypes(
    commonExtension: DefaultExtension,
    extensionType: ExtensionType,
) {


    commonExtension.run {

        buildFeatures {
            buildConfig = true
        }
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {

                    buildTypes {
                        debug {
                            configDebugBuildType()

                        }
                        release {
                            configReleaseBuildType(commonExtension)

                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {

                    buildTypes {
                        debug {
                            configDebugBuildType()
                        }
                        release {

                            configReleaseBuildType(commonExtension)
                            isMinifyEnabled = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                    }
                }
            }


        }


    }
}

private fun BuildType.configDebugBuildType() {


}

private fun BuildType.configReleaseBuildType(
    commonExtension: DefaultExtension,
) {


    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}