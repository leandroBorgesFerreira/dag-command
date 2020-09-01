
![Java CI with Gradle](https://github.com/leandroBorgesFerreira/dag-command/workflows/Java%20CI%20with%20Gradle/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.leandroborgesferreira/dag-command/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.leandroborgesferreira/dag-command/)

# dag-command

Affected modules by your branch. 

This pluggin makes a diff from your branch and the `develop` branch and writes which modules in the graph were affected by your changes. It takes the changes modules and the traverse the dependent modules and return the affected ones. 

The pluggin also writes the dependency graph in a file. 

## Credits

Work extension of this project: https://github.com/hpedrorodrigues/dag-modules

## Configuration
#### Groovy

```
buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
	    classpath 'com.github.leandroborgesferreira:dag-command:1.5.2'
    }
}

apply plugin: 'com.github.leandroborgesferreira.dag-command'

dagCommand {
    filter = "all"
    defaultBranch = "origin/develop"
    outputType = "json"
    printModulesInfo = true
}
```

#### Kotlin

```
import com.github.leandroborgesferreira.dagcommand.DagCommandPlugin
import com.github.leandroborgesferreira.dagcommand.extension.CommandExtension

buildscript {
	 repositories {
    	mavenCentral() // or jcenter()
    }
    
    dependencies {
    	classpath("com.github.leandroborgesferreira:dag-command:1.5.2")
    }
}

apply<DagCommandPlugin>()

the<CommandExtension>().run {
    this.filter = "all"
    this.defaultBranch = "master"
    this.outputType = "file"
    this.printModulesInfo = true
}
```


## Usage

Run the task:

```
./gradlew dag-command
```

New files will be create inside `./build/dag-command` you will be able to find the adjacency list, node list (with information about the nodes) and edge list of your modules, the edge list and the affected modules. 

## Configuration

#### filter
You can filter between library, app, or all (which include both). 

#### defaultBranch
The default branch of your project. This is needed for this plugin to calculate the changes on your PR and which modules were affected by then. 

#### outputType
The type of the files generated. You can choose between `json` or `csv`.

#### printModulesInfo
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

**This module assumes that the modules are in the folders in the root of the project** (that's a very common case). All folders in the root of the project don't need to be modules, but all modules must be in thier on folders in the root. So in this example above, the changed modules would be **A**, **B** and **C**, if they are part of the modules of the project. 

### Limitations
At the moment, this project doesn't detect changes in the root folder, so the configuration of the gradle modules should also a module, it is recommended the **buildSrc** folder. When the pluggin detects changes in the **buildSrc** folder, it considers that all modules were affected by the PR. 
