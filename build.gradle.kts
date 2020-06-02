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

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    plugins {
        register(rootProject.name) {
            id = "$group.dag-command"
            displayName = "Unit tests following the DAG"
            description = "Unit tests only the changed modules in the dependencies graph"
            implementationClass = "$group.dagcommand.DagUnitPluggin"
        }
    }
}

//publishing {
//    repositories {
//        maven { url = URI("file:///${System.getenv("HOME")}/.gradle/caches") }
//    }
//}