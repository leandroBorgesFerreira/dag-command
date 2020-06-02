import java.net.URI

group = "br.com.leandroferreira"
version = "1.0.0"

plugins {
    kotlin("jvm") version ("1.3.72")
    id("java-gradle-plugin")
    id("maven-publish")
}

repositories {
    mavenCentral()
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
            id = "$group.dag-command"
            displayName = "Unit tests following the DAG"
            description = "Unit tests only the changed modules in the dependencies graph"
            implementationClass = "$group.dagcommand.DagCommandPlugin"
        }
    }
}

//publishing {
//    repositories {
//        maven { url = URI("file:///${System.getenv("HOME")}/.gradle/caches") }
//    }
//}
