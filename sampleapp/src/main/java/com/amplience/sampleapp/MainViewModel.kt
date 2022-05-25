package com.amplience.sampleapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.parseToObject
import com.amplience.ampliencesdk.parseToObjectList
import com.amplience.sampleapp.model.Banner
import com.amplience.sampleapp.model.Screen
import com.amplience.sampleapp.model.Slide
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    var appBarTitle by mutableStateOf("")
    var screens by mutableStateOf<List<Screen>>(listOf())

    init {
        getExamples()
    }

    private fun getExamples() {
        viewModelScope.launch {
            val exampleScreens = arrayListOf<Screen>(Screen.HomeScreen())

            val bannerRes = AmplienceManager.getInstance().getContentByKey("example-key")
            if (bannerRes.isSuccess) {
                val map = bannerRes.getOrNull() ?: return@launch
                Timber.d("Map $map")
                val banner = map.content.parseToObject<Banner>()
                Timber.d("Banner $banner")

                if (banner != null) {
                    exampleScreens.add(Screen.BannerScreen(banner))
                }
            } else {
                Timber.e(bannerRes.exceptionOrNull())
            }

            val slidesRes = AmplienceManager.getInstance()
                .getContentById("bd89c2ed-0ed5-4304-8c89-c0710af500e2")
            if (slidesRes.isSuccess) {
                val map = slidesRes.getOrNull() ?: return@launch
                val slides = map.content.parseToObjectList<Slide>("slides")
                Timber.d("Slides $slides")

                if (slides != null) {
                    exampleScreens.add(Screen.SlidesScreen(slides))
                }
            } else {
                Timber.e(bannerRes.exceptionOrNull())
            }

            this@MainViewModel.screens = exampleScreens
        }
    }
}
