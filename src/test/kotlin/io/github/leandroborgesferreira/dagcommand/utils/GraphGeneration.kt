package io.github.leandroborgesferreira.dagcommand.utils

import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import io.github.leandroborgesferreira.dagcommand.domain.Edge

fun testProject() = listOf(
    DagProject(path = ":application", outputName = "application", dependencies = emptySet()),
    DagProject(path = ":backend", outputName = "backend", dependencies = emptySet()),
    DagProject(path = ":common", outputName = "common", dependencies = emptySet()),
    DagProject(path = ":libraries", outputName = "libraries", dependencies = emptySet()),
    DagProject(path = ":plugins", outputName = "plugins", dependencies = emptySet()),
    DagProject(path = ":writeopia", outputName = "writeopia", dependencies = emptySet()),
    DagProject(path = ":writeopia_models", outputName = "writeopia_models", dependencies = emptySet()),
    DagProject(path = ":writeopia_ui", outputName = "writeopia_ui", dependencies = emptySet()),
    DagProject(path = ":application:androidApp", outputName = "androidApp", dependencies = emptySet()),
    DagProject(path = ":application:core", outputName = "core", dependencies = emptySet()),
    DagProject(path = ":application:desktopApp", outputName = "desktopApp", dependencies = emptySet()),
    DagProject(path = ":application:features", outputName = "features", dependencies = emptySet()),
    DagProject(path = ":application:web", outputName = "web", dependencies = emptySet()),
    DagProject(path = ":backend:documents", outputName = "documents", dependencies = emptySet()),
    DagProject(path = ":backend:editor", outputName = "editor", dependencies = emptySet()),
    DagProject(path = ":common:endpoints", outputName = "endpoints", dependencies = emptySet()),
    DagProject(path = ":libraries:dbtest", outputName = "dbtest", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_export", outputName = "writeopia_export", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_import_document", outputName = "writeopia_import_document", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_network", outputName = "writeopia_network", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_persistence_core", outputName = "writeopia_persistence_core", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_persistence_room", outputName = "writeopia_persistence_room", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_persistence_sqldelight", outputName = "writeopia_persistence_sqldelight", dependencies = emptySet()),
    DagProject(path = ":plugins:writeopia_serialization", outputName = "writeopia_serialization", dependencies = emptySet()),
    DagProject(path = ":application:core:auth_core", outputName = "auth_core", dependencies = emptySet()),
    DagProject(path = ":application:core:common_ui", outputName = "common_ui", dependencies = emptySet()),
    DagProject(path = ":application:core:common_ui_tests", outputName = "common_ui_tests", dependencies = emptySet()),
    DagProject(path = ":application:core:persistence_bridge", outputName = "persistence_bridge", dependencies = emptySet()),
    DagProject(path = ":application:core:persistence_room", outputName = "persistence_room", dependencies = emptySet()),
    DagProject(path = ":application:core:persistence_sqldelight", outputName = "persistence_sqldelight", dependencies = emptySet()),
    DagProject(path = ":application:core:resources", outputName = "resources", dependencies = emptySet()),
    DagProject(path = ":application:core:utils", outputName = "utils", dependencies = emptySet()),
    DagProject(path = ":application:features:account", outputName = "account", dependencies = emptySet()),
    DagProject(path = ":application:features:auth", outputName = "auth", dependencies = emptySet()),
    DagProject(path = ":application:features:editor", outputName = "editor", dependencies = emptySet()),
    DagProject(path = ":application:features:note_menu", outputName = "note_menu", dependencies = emptySet()),
    DagProject(path = ":backend:documents:documents_spring", outputName = "documents_spring", dependencies = emptySet()),
    DagProject(path = ":backend:editor:api_editor", outputName = "api_editor", dependencies = emptySet()),
    DagProject(path = ":backend:editor:api_editor_socket", outputName = "api_editor_socket", dependencies = emptySet()),
    DagProject(path = ":backend:editor:api_editor_spring", outputName = "api_editor_spring", dependencies = emptySet()),
)

fun simpleGraph(): Map<String, Set<String>> =
    mapOf(
        ":A" to setOf(":B", ":C", ":D"),
        ":B" to setOf(":C", ":F"),
        ":C" to setOf(":E", ":F"),
        ":D" to setOf(":E", ":F"),
        ":E" to setOf(":F"),
        ":F" to emptySet()
    )

fun graphWithCycle(): Map<String, Set<String>> =
    mapOf(
        ":A" to setOf(":B"),
        ":B" to setOf(":C"),
        ":C" to setOf(":A"),
    )

fun simpleEdgeList(): List<Edge> =
    listOf(
        Edge(":A", ":B", 1),
        Edge(":A", ":C", 1),
        Edge(":A", ":D", 1),
        Edge(":B", ":C", 1),
        Edge(":B", ":F", 1),
        Edge(":C", ":E", 1),
        Edge(":C", ":F", 1),
        Edge(":D", ":E", 1),
        Edge(":D", ":F", 1),
        Edge(":E", ":F", 1)
    )

fun disconnectedGraph(): Map<String, Set<String>> =
    mapOf(
        ":A" to emptySet(),
        ":B" to emptySet(),
        ":C" to emptySet(),
        ":D" to emptySet(),
        ":E" to emptySet(),
        ":F" to emptySet()
    )

fun cyclicalObjectGraph(): List<GraphNode> =
    listOf(
        GraphNode(
            path = ":A",
            name = "A",
            next = listOf(
                GraphNode(
                    path = ":B",
                    name = "B",
                    next = listOf(
                        GraphNode(path = ":A", name = "A")
                    )
                )
            )
        )
    )


fun objectGraph(): List<GraphNode> =
    listOf(
        GraphNode(
            path = ":A",
            name = "A",
            next = listOf(
                GraphNode(
                    path = ":B",
                    name = "B",
                    next = listOf(
                        GraphNode(path = ":E", name = "E")
                    )
                )
            )
        ),
        GraphNode(
            path = ":C",
            name = "C",
            next = listOf(
                GraphNode(
                    path = ":B",
                    name = "B",
                    next = listOf(
                        GraphNode(path = ":E", name = "E")
                    )
                )
            )
        ),
        GraphNode(
            path = ":D",
            name = "D",
            next = listOf(
                GraphNode(
                    path = ":B",
                    name = "B",
                    next = listOf(
                        GraphNode(path = ":E", name = "E")
                    )
                )
            )
        ),
    )

data class GraphNode(
    val path: String,
    val name: String,
    val next: Iterable<GraphNode> = emptySet()
)
