plugins {
    `java-library`
}

val edcScmUrl: String by project
val edcScmConnection: String by project
val edcBuildVersion = libs.versions.edc.build

buildscript {
    dependencies {
        classpath(libs.edc.build) {
            exclude("org.jetbrains", "annotations")
            exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        }
    }
}

allprojects {
    apply(plugin = "${group}.edc-build")

    configure<org.eclipse.edc.plugins.autodoc.AutodocExtension> {
        processorVersion.set(edcBuildVersion)
        outputDirectory.set(project.layout.buildDirectory.asFile)
    }

    configure<org.eclipse.edc.plugins.edcbuild.extensions.BuildExtension> {
        pom {
            scmUrl.set(edcScmUrl)
            scmConnection.set(edcScmConnection)
        }
    }

}
