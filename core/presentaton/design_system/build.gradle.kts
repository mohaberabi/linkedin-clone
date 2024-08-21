plugins {
    alias(libs.plugins.linkedinclone.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = libs.versions.projectDomain.get() + ".core.presentation.design_system"

}