# dag-command

Affected modules by your branch. 

This pluggin makes a diff from your branch and the `develop` branch and writes which modules in the graph were affected by your changes. It takes the changes modules and the traverse the dependent modules and return the affected ones. 


## Credits

Work extension of this project: https://github.com/hpedrorodrigues/dag-modules

## How to use

Still on wip. But you can use like this using your local maven

```
buildscript {

    repositories {
        mavenLocal()
    }

    dependencies {
		 classpath 'br.com.leandroferreira:dag-command:1.0.0'
    }
}

apply plugin: 'br.com.leandroferreira.dag-command'

dagCommand {
    filter = "all"
    defaultBranch = "develop"
    outputType = "console"
}
```

