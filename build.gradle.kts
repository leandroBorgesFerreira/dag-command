import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

val artifactIdVal = "dag-command"
val versionVal = "1.9.0"
val publicationName = "dagCommand"

group = "io.github.leandroborgesferreira"
version = versionVal

plugins {
    kotlin("jvm") version "1.9.22"
    id("java-gradle-plugin")
    signing
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.2.1"
}

sourceSets {
    test {
        resources {
            srcDirs("src/test/resources")
        }
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.22")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.mockito:mockito-core:5.10.0")
}

tasks.withType<Copy>().all { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }

//Publication code. It would be interesting to separate to another script.
java {
    withJavadocJar()
    withSourcesJar()
}

gradlePlugin {
    website.set("https://github.com/leandroBorgesFerreira/dag-command/")
    vcsUrl.set("https://github.com/leandroBorgesFerreira/dag-command/")

    plugins {
        register(rootProject.name) {
            id = "$group.$artifactIdVal"
            displayName = "Unit tests following the DAG"
            description = "Unit tests only the changed modules in the dependencies graph"
            implementationClass = "$group.dagcommand.DagCommandPlugin"
            tags.set(listOf("testing", "tooling", "multi-module"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = group.toString()
            artifactId = artifactIdVal
            version = versionVal

            from(components["java"])

            pom {
                name.set("Dag Command")
                description.set("Affected gradle modules by branch")
                url.set("https://github.com/leandroBorgesFerreira/dag-command/")
                packaging = "jar"
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("leandroBorgesFerreira")
                        name.set("Leandro Borges Ferreira")
                        email.set("lehen01@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/leandroBorgesFerreira/dag-command.git")
                    developerConnection.set("scm:git:ssh://github.com/leandroBorgesFerreira/dag-command.git")
                    url.set("https://github.com/leandroBorgesFerreira/dag-command")
                }
            }
        }
    }
}

signing {
    val secretPropsFile: File = project.rootProject.file("local.properties")

    if (secretPropsFile.exists()) {
        val prop = Properties().apply {
            load(FileInputStream(secretPropsFile))
        }
        useInMemoryPgpKeys(
            prop.getProperty("signing.keyId"),
            prop.getProperty("signing.key"),
            prop.getProperty("signing.password"),
        )
    } else {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY_ID"),
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD"),
        )
    }

    sign(publishing.publications[publicationName])
}
