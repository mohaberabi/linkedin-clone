plugins {

    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = libs.versions.projectDomain.get() + ".onboarding"


}

dependencies {
    implementation(projects.core.domain)
}