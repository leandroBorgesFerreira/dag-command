val artifactIdVal = "dag-command"
val versionVal = "1.0.0-SNAPSHOT"

group = "com.github.leandroborgesferreira"
version = versionVal

plugins {
    kotlin("jvm") version ("1.3.72")
    id("java-gradle-plugin")
    id("maven-publish")
    signing
    `maven-publish`
}

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

signing {
    sign(configurations.archives.get())
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.google.code.gson:gson:2.8.6")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.mockito:mockito-core:3.3.3")
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
        create<MavenPublication>("DagCommand") {
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
                username = project.property("nexusUsername").toString()
                password = project.property("nexusPassword").toString()
            }

            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")

            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}
