rootProject.name = "runtime-metamodel"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}

include(":runtime-metamodel")
include(":plugins:autodoc:autodoc-plugin")
include(":plugins:autodoc:autodoc-processor")
include(":plugins:autodoc:autodoc-converters")