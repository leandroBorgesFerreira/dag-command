# dag-command

Affected modules by your branch. 

This pluggin makes a diff from your branch and the `develop` branch and writes which modules in the graph were affected by your changes. It takes the changes modules and the traverse the dependent modules and return the affected ones. 


## Credits

Work extension of this project: https://github.com/hpedrorodrigues/dag-modules

## Configuration

Still on wip. But you can use like this using your local maven

```
buildscript {

    repositories {
        mavenLocal()
    }

    dependencies {
		 classpath 'com.github.leandroferreira:dag-command:1.0.0'
    }
}

apply plugin: 'com.github.leandroferreira.dag-command'

dagCommand {
    filter = "all"
    defaultBranch = "develop"
    outputType = "console"
}
```

## Run

Run the task:

```
./gradlew dag-command
```
