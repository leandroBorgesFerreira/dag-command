import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

val artifactIdVal = "dag-command"
val versionVal = "1.6.0"
val publicationName = "dagCommand"

group = "com.github.leandroborgesferreira"
version = versionVal

fun getNexusUserName(): String? = System.getenv("SONATYPE_NEXUS_USERNAME")
fun getNexusPassword(): String? = System.getenv("SONATYPE_NEXUS_PASSWORD")

plugins {
    kotlin("jvm") version "1.8.21"
    id("java-gradle-plugin")
    signing
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

apply(from = "${rootDir}/scripts/publish-root.gradle")

sourceSets {
    test {
        resources {
            srcDirs("src/test/resources")
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-core:5.3.1")
}

gradlePlugin {
    plugins {
        register(rootProject.name) {
            id = "$group.$artifactIdVal"
            displayName = "Unit tests following the DAG"
            description = "Unit tests only the changed modules in the dependencies graph"
            implementationClass = "$group.dagcommand.DagCommandPlugin"
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
    repositories {
        maven {
            credentials {
                username = getNexusUserName()
                password = getNexusPassword()
            }

            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")

            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

signing {
    if (System.getenv("IS_CI") == "true") {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY_ID"),
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD"),
        )
    } else {
        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "local.properties")))
        }

        useInMemoryPgpKeys(
            prop.getProperty("signing.keyId") as String,
            prop.getProperty("signing.key") as String,
            prop.getProperty("signing.password"),
        )
    }

    sign(publishing.publications[publicationName])
}

tasks.withType<Copy>().all { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
