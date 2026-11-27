plugins {
    `java-library`
    alias(libs.plugins.edc.build)
}

val edcBuildId = libs.plugins.edc.build.get().pluginId

allprojects {
    pluginManager.apply(edcBuildId)

    configure<org.eclipse.edc.plugins.edcbuild.extensions.BuildExtension> {
        pom {
            scmUrl.set(project.property("edcScmUrl") as String)
            scmConnection.set(project.property("edcScmConnection") as String)
        }
    }

}
