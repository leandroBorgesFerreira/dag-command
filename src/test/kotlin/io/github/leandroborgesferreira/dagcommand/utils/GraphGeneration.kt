package io.github.leandroborgesferreira.dagcommand.utils

import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import io.github.leandroborgesferreira.dagcommand.domain.Edge

fun testProject() = listOf(
    DagProject(fullGradlePath = ":application", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend", dependencies = emptySet()),
    DagProject(fullGradlePath = ":common", dependencies = emptySet()),
    DagProject(fullGradlePath = ":libraries", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins", dependencies = emptySet()),
    DagProject(fullGradlePath = ":writeopia", dependencies = emptySet()),
    DagProject(fullGradlePath = ":writeopia_models", dependencies = emptySet()),
    DagProject(fullGradlePath = ":writeopia_ui", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:androidApp", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:desktopApp", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:features", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:web", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend:documents", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend:editor", dependencies = emptySet()),
    DagProject(fullGradlePath = ":common:endpoints", dependencies = emptySet()),
    DagProject(fullGradlePath = ":libraries:dbtest", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_export", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_import_document", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_network", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_persistence_core", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_persistence_room", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_persistence_sqldelight", dependencies = emptySet()),
    DagProject(fullGradlePath = ":plugins:writeopia_serialization", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:auth_core", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:common_ui", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:common_ui_tests", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:persistence_bridge", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:persistence_room", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:persistence_sqldelight", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:resources", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:core:utils", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:features:account", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:features:auth", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:features:editor", dependencies = emptySet()),
    DagProject(fullGradlePath = ":application:features:note_menu", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend:documents:documents_spring", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend:editor:api_editor", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend:editor:api_editor_socket", dependencies = emptySet()),
    DagProject(fullGradlePath = ":backend:editor:api_editor_spring", dependencies = emptySet()),
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
            path = "A",
            next = listOf(
                GraphNode(
                    path = "B",
                    next = listOf(
                        GraphNode(path = "A")
                    )
                )
            )
        )
    )


fun objectGraph(): List<GraphNode> =
    listOf(
        GraphNode(
            path = "A",
            next = listOf(
                GraphNode(
                    path = "B",
                    next = listOf(
                        GraphNode(path = "E")
                    )
                )
            )
        ),
        GraphNode(
            path = "C",
            next = listOf(
                GraphNode(
                    path = "B",
                    next = listOf(
                        GraphNode(path = "E")
                    )
                )
            )
        ),
        GraphNode(
            path = "D",
            next = listOf(
                GraphNode(
                    path = "B",
                    next = listOf(
                        GraphNode(path = "E")
                    )
                )
            )
        ),
    )

data class GraphNode(
    val path: String,
    val next: Iterable<GraphNode> = emptySet()
)
