package com.example.analyseservice.service

import com.example.analyseservice.model.GraphNode
import com.example.analyseservice.model.GraphProperty
import com.example.analyseservice.model.GraphRelation
import com.example.analyseservice.model.response.GraphResponse
import org.ejml.simple.SimpleMatrix
import org.springframework.stereotype.Service
import smile.clustering.DBSCAN
import kotlin.random.Random

@Service
class AnalyseGraph {

    fun mergeGraphs(graph1: GraphResponse, graph2: GraphResponse, graph3: GraphResponse): GraphResponse {
        val nodesMap = mutableMapOf<String, GraphNode>()

        listOf(graph1, graph2, graph3).flatMap { it.nodes }.forEach { node ->
            nodesMap.compute(node.id) { _, existingNode ->
                if (existingNode == null) {
                    node
                } else {
                    val newProperties = mergeProperties(listOf(existingNode.properties, node.properties))
                    GraphNode(id = node.id, labels = (existingNode.labels + node.labels).distinct(), properties = newProperties)
                }
            }
        }

        val relationsMap = mutableMapOf<String, GraphRelation>()

        listOf(graph1, graph2, graph3).flatMap { it.relationships }.forEach { relation ->
            relationsMap[relation.id] = relation
        }

        return GraphResponse(
                nodes = nodesMap.values.toList(),
                relationships = relationsMap.values.toList()
        )
    }

    fun clusterData(averagedVectors: Map<String, List<Double>>): DBSCAN<DoubleArray> {
        val data = prepareDataForDBSCAN(averagedVectors)
        val epsilon = 0.062
        val minPts = 3
        return performDBSCAN(data, epsilon, minPts)
    }

    fun averageEmbeddingsByUser(userIds: List<String>, embeddings: List<List<Double>>): Map<String, List<Double>> {
        val embeddingsByUser: Map<String, MutableList<List<Double>>> = userIds.zip(embeddings).groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
        ).mapValues { entry -> entry.value.toMutableList() }

        return embeddingsByUser.mapValues { (_, vectors) ->
            averageOfVectors(vectors)
        }
    }

    private fun averageOfVectors(vectors: List<List<Double>>): List<Double> {
        val dimension = vectors.first().size
        val sumVector = DoubleArray(dimension) { 0.0 }

        vectors.forEach { vector ->
            vector.forEachIndexed { index, value ->
                sumVector[index] += value
            }
        }
        return sumVector.toList().map { it / vectors.size }
    }

    fun spectralEmbedding(adjMatrix: SimpleMatrix, dimensions: Int): List<List<Double>> {
        val evDecomp = adjMatrix.eig()

        val vectors = ArrayList<SimpleMatrix>()
        val values = DoubleArray(adjMatrix.numRows())

        for (i in 0 until evDecomp.numberOfEigenvalues) {
            vectors.add(evDecomp.getEigenVector(i)!!)
            values[i] = evDecomp.getEigenvalue(i).real
        }

        val sortedIndices = values.indices.sortedByDescending { values[it] }

        val embeddings = vectors.map { vector ->
            sortedIndices.take(dimensions).map { eigenIndex ->
                vector[eigenIndex, 0]
            }
        }
        return embeddings
    }

    fun calculateEmptyWeights(graphResponse: GraphResponse): Map<Pair<String, String>, Double> {
        val graph = buildGraph(graphResponse)
        return calculateEdgeWeights(graph, numWalks = 100, walkLength = 10)
    }

    private fun performDBSCAN(data: Array<DoubleArray>, epsilon: Double, minPts: Int): DBSCAN<DoubleArray> {
        return DBSCAN(data, epsilon, minPts)
    }

    private fun prepareDataForDBSCAN(averagedVectors: Map<String, List<Double>>): Array<DoubleArray> {
        return averagedVectors.values.map { it.toDoubleArray() }.toTypedArray()
    }

    private fun buildAdjacencyMatrix(graph: Map<String, List<String>>, weights: Map<Pair<String, String>, Double>, nodes: List<String>): SimpleMatrix {
        val size = nodes.size
        val matrix = SimpleMatrix(size, size)

        nodes.forEachIndexed { i, node1 ->
            nodes.forEachIndexed { j, node2 ->
                if (i != j) {
                    val weight = weights[Pair(node1, node2)] ?: 0.0
                    matrix[i, j] = weight
                }
            }
        }

        return matrix
    }

    private fun calculateEdgeWeights(graph: Map<String, List<String>>, numWalks: Int, walkLength: Int): Map<Pair<String, String>, Double> {
        val edgeUsage = mutableMapOf<Pair<String, String>, Int>()

        repeat(numWalks) {
            val startNode = graph.keys.random()
            val path = randomWalk(graph, startNode, walkLength)
            for (i in 0 until path.size - 1) {
                val edge = sortedSetOf(path[i], path[i+1]).toList()
                edgeUsage.merge(Pair(edge[0], edge[1]), 1, Int::plus)
            }
        }

        return edgeUsage.mapValues { entry -> entry.value.toDouble() / numWalks }
    }

    private fun randomWalk(graph: Map<String, List<String>>, startNodeId: String, walkLength: Int): List<String> {
        val path = mutableListOf(startNodeId)
        repeat(walkLength - 1) {
            val currentNodeId = path.last()
            val neighbors = graph[currentNodeId] ?: return path
            path.add(neighbors[Random.nextInt(neighbors.size)])
        }
        return path
    }

    private fun mergeProperties(propertiesList: List<List<GraphProperty>>): List<GraphProperty> {
        val propertyMap = mutableMapOf<String, GraphProperty>()

        propertiesList.flatten().forEach { prop ->
            propertyMap[prop.name] = prop
        }

        return propertyMap.values.toList()
    }

    private fun buildGraph(graphResponse: GraphResponse): Map<String, MutableList<String>> {
        val graph = mutableMapOf<String, MutableList<String>>()

        graphResponse.relationships.forEach { relation ->
            graph.computeIfAbsent(relation.startNodeId) { mutableListOf() }.add(relation.endNodeId)
        }

        return graph
    }
}