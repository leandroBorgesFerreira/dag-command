![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.leandroborgesferreira.dag-command) ![Java CI with Gradle](https://github.com/leandroBorgesFerreira/dag-command/workflows/Java%20CI%20with%20Gradle/badge.svg) 


# dag-command

Affected modules by your branch. 

This pluggin makes a diff from your branch and the main branch of your project and writes which modules in the graph were affected by your changes. It takes the changes modules and the traverse the dependent modules and return the affected ones. 

The pluggin also writes the dependency graph in a file. 

## Credits

Work extension of this project: https://github.com/hpedrorodrigues/dag-modules

## Configuration
You can check the plugin in the gradle plugin portal here: https://plugins.gradle.org/plugin/io.github.leandroborgesferreira.dag-command

#### Groovy

```
buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
	    classpath 'io.github.leandroborgesferreira:dag-command:[version]'
    }
}

apply plugin: 'io.github.leandroborgesferreira.dag-command'

dagCommand {
    filterModules = "[\"filter:your:module:out!\"]"
    defaultBranch = "origin/develop"
    outputType = "json"
    printModulesInfo = true
    useProjectPathAsOutputName = false
    excludeIntermediateModules = false
}
```

#### Kotlin

```kotlin
import io.github.leandroborgesferreira.dagcommand.DagCommandPlugin
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension

buildscript {
    repositories {
    	mavenCentral() // or jcenter()
    }
    
    dependencies {
    	classpath("io.github.leandroborgesferreira:dag-command:[version]")
    }
}

apply<DagCommandPlugin>()

the<CommandExtension>().run {
    this.filterModules = "[\"filter:your:module:out!\"]"
    this.defaultBranch = "master"
    this.outputType = "json"
    this.printModulesInfo = true
    this.useProjectPathAsOutputName = false
    this.excludeIntermediateModules = false
}
```

OR the new structure: 


```kotlin
plugins {
    [...]
    id("io.github.leandroborgesferreira.dag-command") version "[version]" apply true
}

dagCommand {
    filterModules = "[\"filter:your:module:out!\"]"
    defaultBranch = "origin/develop"
    outputType = "json"
    printModulesInfo = true
}
```


## Usage

Run the task:

```
./gradlew dag-command
```

New files will be create inside `./build/dag-command` you will be able to find the adjacency list, node list (with information about the nodes) and edge list of your modules, the edge list and the affected modules. 

## Configuration

```
filterModules
```
You can remove modules that you dont want to include using this parameter.

```
defaultBranch
```
The default branch of your project. This is needed for this plugin to calculate the changes on your PR and which modules were affected by then. 

```
outputType
```
The type of the files generated. You can choose between `json` or `csv`.

```
printModulesInfo
```
Print the information in files. You can choose to disable this functionally to speed up a bit the execution of this plugin (only necessary for really big projects, you most likelly can leave this as `true`.)

## Calculation of the PR changes
It is important to understand how the PR change calculation works. Basically this pluggin runs: 

```
git diff [default branch] --dirstat=files
```

and parses the module accordingly with the folders that were changed. The output from the git command is something like: 

```
"3.4% A/src/test/java/com/module1/android/",
"3.9% A/",
"5.8% B/src/main/java/com/module1/inbox/messages/",
"3.9% B/src/",
"4.4% C/src/main/java/com/module1/network/responsemodels/",
```

**Prior to version 1.10.0, any kind of folder structure is accepted, before it was only possible to have the modules in the root folder.** 

### Limitations
At the moment, this project doesn't detect changes in the root folder. It only detects changes inside your gradle modules, `buildSrc` file and `/gradle` file. If changes detected are inside `buildSrc` or `gradle`, it considers that all modules are affected by the change. 
