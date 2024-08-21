plugins {
    alias(libs.plugins.linkedinclone.android.application)
    alias(libs.plugins.linkedinclone.android.hilt)
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


}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)

    implementation(projects.core.presentaton.designSystem)
    implementation(projects.core.presentaton.ui)
    implementation(projects.posts)
    implementation(projects.addPost)
    implementation(projects.jobs)


}