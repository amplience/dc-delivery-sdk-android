package com.amplience.sampleapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.api.models.ContentResponse
import com.amplience.ampliencesdk.parseToObjectList
import com.amplience.sampleapp.model.Slide
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {
    var ui by mutableStateOf<ContentResponse?>(ContentResponse(mapOf()))
    var slides by mutableStateOf<List<Slide>>(listOf())

    init {
        viewModelScope.launch {
            val res = AmplienceManager.getInstance().getContentById("bd89c2ed-0ed5-4304-8c89-c0710af500e2")
            if (res.isSuccess) {
                val map = res.getOrNull() ?: return@launch
                ui = map
                val slides = map.content.parseToObjectList<Slide>("slides")
                Timber.d("Slides $slides")
                this@MainViewModel.slides = slides ?: listOf()
            }
        }
    }
}
