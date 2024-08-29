plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.linkedinclone.android.hilt)

}

android {
    namespace = "com.mohaberabi.linkedinclone.post_detail"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}