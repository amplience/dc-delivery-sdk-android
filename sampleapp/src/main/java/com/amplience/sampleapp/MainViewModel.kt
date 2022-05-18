package com.amplience.sampleapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.api.models.ContentResponse
import com.amplience.ampliencesdk.media.AmplienceImage
import com.amplience.ampliencesdk.parseToObject
import com.amplience.sampleapp.model.Banner
import com.amplience.sampleapp.model.Slide
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {
    var ui by mutableStateOf<ContentResponse?>(ContentResponse(mapOf()))
    var slides by mutableStateOf<List<Slide>>(listOf())

    var banner by mutableStateOf<Banner?>(null)

    init {
        /*viewModelScope.launch {
            val res = AmplienceManager.getInstance().getContentById("bd89c2ed-0ed5-4304-8c89-c0710af500e2")
            if (res.isSuccess) {
                val map = res.getOrNull() ?: return@launch
                ui = map
                val slides = map.content.parseToObjectList<Slide>("slides")
                Timber.d("Slides $slides")
                this@MainViewModel.slides = slides ?: listOf()
            }
        }*/

        viewModelScope.launch {
            val res = AmplienceManager.getInstance().getContentByKey("example-key")
            if (res.isSuccess) {
                val map = res.getOrNull() ?: return@launch
                Timber.d("Map $map")
                val banner = map.content.parseToObject<Banner>()
                Timber.d("Banner $banner")
                this@MainViewModel.banner = banner
            }
        }
        val image = AmplienceImage("", "Hero-Banner-720-model2", "ampproduct", "cdn.media.amplience.net")
        val test = AmplienceManager.getInstance().getImageUrl(image) {
            width(200)
            height(100)
        }
        Timber.d("Test $test")
        /*

        var test = AmplienceManager.getInstance().getImageUrl(
            image = image,
            width = 100,
            height = 300
        )
        Timber.d(test)
        test = AmplienceManager.getInstance().getImageUrl(
            image = image,
            height = 300,
            quality = 50
        )
        Timber.d(test)
        test = AmplienceManager.getInstance().getImageUrl(
            image = image,
            width = 100,
            height = 100,
            quality = 50,
            scaleMode = ScaleMode.TopCenter
        )
        Timber.d(test)
        test = AmplienceManager.getInstance().getImageUrl(
            image = image,
            height = 300,
            quality = 50,
            scaleMode = ScaleMode.BottomCenter,
            scaleFit = ScaleFit.Center,
            resizeAlgorithm = ResizeAlgorithm.Hermite
        )
        Timber.d(test)*/
    }
}
