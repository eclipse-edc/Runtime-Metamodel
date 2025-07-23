plugins {
    `java-library`
    alias(libs.plugins.edc.build)
}

val edcScmUrl: String by project
val edcScmConnection: String by project
val edcBuildId = libs.plugins.edc.build.get().pluginId

allprojects {
    apply(plugin = edcBuildId)

    configure<org.eclipse.edc.plugins.edcbuild.extensions.BuildExtension> {
        pom {
            scmUrl.set(edcScmUrl)
            scmConnection.set(edcScmConnection)
        }
    }

}
