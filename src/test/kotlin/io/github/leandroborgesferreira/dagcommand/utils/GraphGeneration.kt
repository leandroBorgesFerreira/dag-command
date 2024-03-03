package io.github.leandroborgesferreira.dagcommand.utils

import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import io.github.leandroborgesferreira.dagcommand.domain.Edge

fun testProject() = listOf(
    DagProject(fullName = ":application", dependencies = emptySet()),
    DagProject(fullName = ":backend", dependencies = emptySet()),
    DagProject(fullName = ":common", dependencies = emptySet()),
    DagProject(fullName = ":libraries", dependencies = emptySet()),
    DagProject(fullName = ":plugins", dependencies = emptySet()),
    DagProject(fullName = ":writeopia", dependencies = emptySet()),
    DagProject(fullName = ":writeopia_models", dependencies = emptySet()),
    DagProject(fullName = ":writeopia_ui", dependencies = emptySet()),
    DagProject(fullName = ":application:androidApp", dependencies = emptySet()),
    DagProject(fullName = ":application:core", dependencies = emptySet()),
    DagProject(fullName = ":application:desktopApp", dependencies = emptySet()),
    DagProject(fullName = ":application:features", dependencies = emptySet()),
    DagProject(fullName = ":application:web", dependencies = emptySet()),
    DagProject(fullName = ":backend:documents", dependencies = emptySet()),
    DagProject(fullName = ":backend:editor", dependencies = emptySet()),
    DagProject(fullName = ":common:endpoints", dependencies = emptySet()),
    DagProject(fullName = ":libraries:dbtest", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_export", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_import_document", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_network", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_persistence_core", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_persistence_room", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_persistence_sqldelight", dependencies = emptySet()),
    DagProject(fullName = ":plugins:writeopia_serialization", dependencies = emptySet()),
    DagProject(fullName = ":application:core:auth_core", dependencies = emptySet()),
    DagProject(fullName = ":application:core:common_ui", dependencies = emptySet()),
    DagProject(fullName = ":application:core:common_ui_tests", dependencies = emptySet()),
    DagProject(fullName = ":application:core:persistence_bridge", dependencies = emptySet()),
    DagProject(fullName = ":application:core:persistence_room", dependencies = emptySet()),
    DagProject(fullName = ":application:core:persistence_sqldelight", dependencies = emptySet()),
    DagProject(fullName = ":application:core:resources", dependencies = emptySet()),
    DagProject(fullName = ":application:core:utils", dependencies = emptySet()),
    DagProject(fullName = ":application:features:account", dependencies = emptySet()),
    DagProject(fullName = ":application:features:auth", dependencies = emptySet()),
    DagProject(fullName = ":application:features:editor", dependencies = emptySet()),
    DagProject(fullName = ":application:features:note_menu", dependencies = emptySet()),
    DagProject(fullName = ":backend:documents:documents_spring", dependencies = emptySet()),
    DagProject(fullName = ":backend:editor:api_editor", dependencies = emptySet()),
    DagProject(fullName = ":backend:editor:api_editor_socket", dependencies = emptySet()),
    DagProject(fullName = ":backend:editor:api_editor_spring", dependencies = emptySet()),
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
