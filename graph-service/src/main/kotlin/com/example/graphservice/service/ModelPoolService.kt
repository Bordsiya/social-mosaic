package com.example.graphservice.service

import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.zoo.PretrainedType
import org.deeplearning4j.zoo.ZooModel
import org.springframework.stereotype.Service
import java.util.concurrent.ArrayBlockingQueue

@Service
class ModelPoolService(poolSize: Int = 10) {
    private val modelPool: ArrayBlockingQueue<ComputationGraph> = ArrayBlockingQueue(poolSize)

    init {
        repeat(poolSize) {
            modelPool.add(createModel())
        }
    }

    private fun createModel(): ComputationGraph {
        val zooModel: ZooModel<*> = InceptionV3.builder().build()
        return zooModel.initPretrained(PretrainedType.IMAGENET) as ComputationGraph
    }

    fun borrowModel(): ComputationGraph = modelPool.take()

    fun returnModel(model: ComputationGraph) {
        modelPool.offer(model)
    }
}