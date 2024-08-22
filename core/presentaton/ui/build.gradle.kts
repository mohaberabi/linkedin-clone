plugins {
    alias(libs.plugins.linkedinclone.android.library)
}

android {
    viewBinding { enable = true }
    namespace = libs.versions.projectDomain.get() + ".core.presentation.ui"
}
dependencies {
    implementation(projects.core.presentaton.designSystem)
    implementation(projects.core.domain)
    api(libs.navUI)
    api(libs.navFragment)
}