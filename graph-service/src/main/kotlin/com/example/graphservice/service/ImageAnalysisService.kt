package com.example.graphservice.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.indexing.NDArrayIndex
import org.datavec.image.loader.NativeImageLoader
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

@Service
class ImageAnalysisService(private val modelPoolService: ModelPoolService) {

    suspend fun classifyImage(imgThemesAmount: Int, imageUrl: URL): List<String> = withContext(Dispatchers.IO) {
        val imageFile = getImageFromUrl(imageUrl.toString())
        val loader = NativeImageLoader(299, 299, 3)
        val image = loader.asMatrix(imageFile)

        val scaler = ImagePreProcessingScaler(0.0, 1.0)
        scaler.transform(image)

        val model = modelPoolService.borrowModel()
        try {
            val output = model.output(false, image)[0]
            decodePredictions(output, imgThemesAmount)
        } finally {
            modelPoolService.returnModel(model)
        }
    }

    private fun decodePredictions(output: INDArray, imageThemesAmount: Int): List<String> {
        val top = Nd4j.argsort(output, 1, false).get(NDArrayIndex.point(0), NDArrayIndex.interval(0, imageThemesAmount))
        return top.toIntVector().map { index ->
            labels.getLabel(index)
        }
    }

    private fun getImageFromUrl(url: String): BufferedImage? = withContext(Dispatchers.IO) {
        URL(url).openStream().use { inputStream ->
            ImageIO.read(inputStream)
        }
    }
}