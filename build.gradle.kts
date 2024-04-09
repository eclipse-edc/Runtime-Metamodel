plugins {
    `java-library`
}

val edcScmUrl: String by project
val edcScmConnection: String by project

buildscript {
    dependencies {
        val edcGradlePluginsVersion: String by project
        classpath("org.eclipse.edc.edc-build:org.eclipse.edc.edc-build.gradle.plugin:${edcGradlePluginsVersion}")
    }
}

allprojects {
    apply(plugin = "${group}.edc-build")

    // let's not generate any reports because that is done from within the GitHub Actions workflow
    tasks.withType<Checkstyle> {
        reports {
            html.required.set(false)
            xml.required.set(true)
        }
    }

    configure<org.eclipse.edc.plugins.edcbuild.extensions.BuildExtension> {
        pom {
            scmUrl.set(edcScmUrl)
            scmConnection.set(edcScmConnection)
        }
    }

}
