plugins {
    alias(libs.plugins.linkedinclone.android.application)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.crashlytics)
}

android {

    viewBinding { enable = true }

    namespace = libs.versions.projectApplicationId.get()

    defaultConfig {
        applicationId = libs.versions.projectApplicationId.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    flavorDimensions += "default"

    productFlavors {

        create("development") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            dimension = "default"
        }

        create("production") {
            dimension = "default"
        }
    }


}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)

    implementation(projects.core.presentaton.designSystem)
    implementation(projects.core.presentaton.ui)
    implementation(projects.posts)
    implementation(projects.addPost)
    implementation(projects.jobs)
    implementation(projects.jobDetail)
    implementation(projects.core.domain)
    implementation(libs.core.splashscreen)
    implementation(projects.register)
    implementation(projects.profile)
    implementation(projects.userMedia)
    implementation(projects.core.remoteLogging)
    implementation(projects.core.remoteAnayltics)
}