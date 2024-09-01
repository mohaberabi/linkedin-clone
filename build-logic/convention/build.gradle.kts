@Suppress("DSL_SCOPE_VIOLATION")
plugins {

    `kotlin-dsl`

}


group = "com.mohaberabi.linkedin.buildlogic"

dependencies {

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)

}
enum class AppConvPlugins(
    val id: String,
    val label: String,
    val impl: String
) {
    AndroidApp(
        id = "linkedinclone.android.application",
        label = "androidApplicationConventionPlugin",
        impl = "AndroidApplicationConventionPlugin"
    ),
    AndroidLib(
        id = "linkedinclone.android.library",
        label = "androidLibraryConventionPlugin",
        impl = "AndroidLibraryConventionPlugin"
    ),

    AndroidFeature(
        id = "linkedinclone.android.feature",
        label = "androidFeatureConventionPlugin",
        impl = "AndroidFeatureConventionPlugin"
    ),
    AndroidHilt(
        id = "linkedinclone.android.hilt",
        label = "androidHiltConventionPlugin",
        impl = "AndroidHiltConventionPlugin"
    ),
    Jvm(
        id = "linkedinclone.jvm.library",
        label = "jvmConventionPlugin",
        impl = "JvmLibraryConventionPlugin"
    ),
    Room(
        id = "linkedinclone.room.android",
        label = "androidRoomConventionPlugin",
        impl = "AndroidRoomConventionPlugin"
    )
}

gradlePlugin {
    plugins {
        AppConvPlugins.values().forEach {
            register(it.label) {
                id = it.id
                implementationClass = it.impl
            }
        }
    }
}