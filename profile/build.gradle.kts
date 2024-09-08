plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.safe.args)
}

android {
    namespace = libs.versions.projectDomain.get() + ".profile"

}


dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.currentUser)
    implementation(projects.core.userMetadata)

}