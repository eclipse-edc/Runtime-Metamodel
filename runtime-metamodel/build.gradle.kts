plugins {
    `java-library`
    `maven-publish`
    signing
}

dependencies {
    api(libs.jetbrains.annotations)
    api(libs.jackson.core)
    api(libs.jackson.annotations)
    api(libs.jackson.databind)
    api(libs.jackson.datatypeJsr310)

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.params)
    testImplementation(libs.assertj)
    testImplementation(libs.mockito)
    testRuntimeOnly(libs.junit.engine)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
}

publishing {
    publications {
        create<MavenPublication>("runtime-metamodel") {
            artifactId = "runtime-metamodel"
            from(components["java"])
        }
    }
}

java {
    val javaVersion = 17
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
    tasks.withType(JavaCompile::class.java) {
        // making sure the code does not use any APIs from a more recent version.
        // Ref: https://docs.gradle.org/current/userguide/building_java_projects.html#sec:java_cross_compilation
        options.release.set(javaVersion)
    }
    withJavadocJar()
    withSourcesJar()
}

