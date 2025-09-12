package io.github.leandroborgesferreira.dagcommand.extension

open class CommandExtension(
    var defaultBranch: String = "master",
    var outputType: String = "console",
    var outputPath: String? = null,
    var printModulesInfo: Boolean = true,
    var filterModules: String? = null,

    /**
     * Whether to use the full project path or just the project name in the output.
     * For example:
     *
     * | Full Gradle project path       | Project name       |
     * |--------------------------------|--------------------|
     * | :login                         | login              |
     * | :feature:login                 | login              |
     * | :core:common                   | common             |
     *
     */
    var useProjectPathAsOutputName: Boolean = false,

    /**
     * If true, it will exclude intermediate projects.
     * For example, consider the following project structure:
     * 
     * - Project ':A'
     * - Project ':B'
     *     - Project ':B:foo'
     *     - Project ':B:bar'
     *  
     *  In this scenario, Project ':B' is considered an intermediate project.
     */
    var excludeIntermediateModules: Boolean = false,
)
