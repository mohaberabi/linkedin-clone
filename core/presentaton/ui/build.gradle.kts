plugins {
    alias(libs.plugins.linkedinclone.android.library)
}

android {
    namespace = libs.versions.projectDomain.get() + ".core.presentation.ui"
}
dependencies {
    implementation(projects.core.presentaton.designSystem)
    implementation(projects.core.domain)

}