plugins {
    alias(libs.plugins.linkedinclone.android.feature)

    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + ".posts"

}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androdix.pull.refresh)

    implementation(projects.core.reactToPost)
    implementation(projects.core.currentUser)
    implementation(projects.core.postSaver)
    implementation(projects.core.remoteLogging)

    implementation(projects.core.remoteAnayltics)

}