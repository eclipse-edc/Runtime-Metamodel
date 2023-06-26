plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    checkstyle
    `java-library`
    `version-catalog`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

val group: String by project
val edcScmUrl: String by project
val edcScmConnection: String by project

allprojects {
    apply(plugin = "checkstyle")
    apply(plugin = "maven-publish")

    // let's not generate any reports because that is done from within the GitHub Actions workflow
    tasks.withType<Checkstyle> {
        reports {
            html.required.set(false)
            xml.required.set(true)
        }
    }

    tasks.withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        metaInf {
            from("${rootProject.projectDir.path}/NOTICE.md")
            from("${rootProject.projectDir.path}/LICENSE")
        }
    }

    afterEvaluate {
        // values needed for publishing
        val websiteUrl: String by project
        val developerId: String by project
        val developerName: String by project
        val developerEmail: String by project
        val scmConnection: String by project
        val scmUrl: String by project
        publishing {
            publications.forEach { i ->
                val mp = (i as MavenPublication)
                mp.pom {
                    name.set(project.name)
                    description.set("Runtime metamodel for annotations of the Eclipse Dataspace Components")
                    url.set(websiteUrl)

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                        developers {
                            developer {
                                id.set(developerId)
                                name.set(developerName)
                                email.set(developerEmail)
                            }
                        }
                        scm {
                            connection.set(scmConnection)
                            url.set(scmUrl)
                        }
                    }
                }
            }
            if (!project.hasProperty("skip.signing")) {
                signing {
                    useGpgCmd()
                    sign(publishing.publications)
                }
            }
        }
    }

}
// configure checkstyle version
checkstyle {
    toolVersion = "10.0"
    maxErrors = 0 // does not tolerate errors
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getenv("OSSRH_USER") ?: return@sonatype)
            password.set(System.getenv("OSSRH_PASSWORD") ?: return@sonatype)
        }
    }
}