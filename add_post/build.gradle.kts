plugins {
    alias(libs.plugins.linkedinclone.android.feature)

    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + ".add_posts"

}


dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentaton.designSystem)
    implementation(projects.core.presentaton.ui)
    implementation(projects.core.firestore)
}