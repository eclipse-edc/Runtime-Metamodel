rootProject.name = "runtime-metamodel"

include(":runtime-metamodel")
include(":version-catalog")

pluginManagement {
    repositories {
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
