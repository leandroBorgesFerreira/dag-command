# dag-command

Commands running only on desired modules

## Credits

Work extension of this project: https://github.com/hpedrorodrigues/dag-modules

##How to use

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
```
