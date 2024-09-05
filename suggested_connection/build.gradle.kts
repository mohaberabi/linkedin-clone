plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)

}

android {
    namespace = libs.versions.projectDomain.get() + ".suggested_connection"

}


dependencies {

    implementation(projects.core.data)

    implementation(projects.core.domain)

}