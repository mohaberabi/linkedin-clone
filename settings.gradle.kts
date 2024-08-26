pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LinkedInClone"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core:presentaton:design_system")
include(":core:presentaton:ui")
include(":posts")
include(":jobs")
include(":add_post")
include(":core:domain")
include(":core:data")
include(":job_detail")
include(":register")
include(":profile")
include(":user_media")
include(":core:remote-logging")
include(":core:remote_anayltics")
