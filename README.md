
![Java CI with Gradle](https://github.com/leandroBorgesFerreira/dag-command/workflows/Java%20CI%20with%20Gradle/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.leandroborgesferreira/dag-command/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.leandroborgesferreira/dag-command/)

# dag-command

Affected modules by your branch. 

This pluggin makes a diff from your branch and the `develop` branch and writes which modules in the graph were affected by your changes. It takes the changes modules and the traverse the dependent modules and return the affected ones. 

The pluggin also writes the dependency graph in a file. 

## Credits

Work extension of this project: https://github.com/hpedrorodrigues/dag-modules

## Configuration

```
buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
	classpath 'com.github.leandroborgesferreira:dag-command:1.0.3'
    }
}

apply plugin: 'com.github.leandroborgesferreira.dag-command'

dagCommand {
    filter = "all"
    defaultBranch = "develop"
    outputType = "file"
}
```

## Run

Run the task:

```
./gradlew dag-command
```
