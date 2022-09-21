package com.amplience.sampleapp

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplience.sdk.delivery.android.ContentClient
import com.amplience.sdk.delivery.android.api.models.FilterBy
import com.amplience.sdk.delivery.android.api.models.SortBy
import com.amplience.sdk.delivery.android.parseToObject
import com.amplience.sdk.delivery.android.parseToObjectList
import com.amplience.sampleapp.model.Banner
import com.amplience.sampleapp.model.BlogPost
import com.amplience.sampleapp.model.ImageSlide
import com.amplience.sampleapp.model.Screen
import com.amplience.sampleapp.model.Slide
import com.amplience.sampleapp.model.Text
import com.amplience.sampleapp.model.Video
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    var appBarTitle by mutableStateOf("")
    var screens by mutableStateOf<List<Screen>>(listOf())
    var lowBandwidth: Boolean = false
    var applicationContext: Context? = null
    var stagingEnvironmentUrl: String? = null

    init {
        getExamples()
    }

    fun getExamples() {
        viewModelScope.launch {
            val exampleScreens = arrayListOf<Screen>(Screen.HomeScreen())

            if (applicationContext != null) {
                val docsPortalContentClient = ContentClient.newInstance(
                    applicationContext!!,
                    "docsportal",
                    configuration = ContentClient.Configuration(stagingEnvironmentUrl = stagingEnvironmentUrl)
                )
                val bannerRes = docsPortalContentClient.getContentByKey("banner-example")
                if (bannerRes.isSuccess) {
                    val map = bannerRes.getOrNull() ?: return@launch
                    val banner = map.content?.parseToObject<Banner>()

                    if (banner != null) {
                        exampleScreens.add(Screen.BannerScreen(banner))
                    }
                } else {
                    Timber.e(bannerRes?.exceptionOrNull())
                }
            }

            val slidesRes = ContentClient.getInstance()
                .getContentById("bd89c2ed-0ed5-4304-8c89-c0710af500e2")
            if (slidesRes.isSuccess) {
                val map = slidesRes.getOrNull() ?: return@launch
                val slides = map.content?.parseToObjectList<Slide>("slides")
                Timber.d("Slides $slides")

                if (slides != null) {
                    exampleScreens.add(Screen.SlidesScreen(slides))
                }
            } else {
                Timber.e(slidesRes.exceptionOrNull())
            }

            val exampleContentRes =
                ContentClient.getInstance().getContentByKey("example-content-items")
            if (exampleContentRes.isSuccess) {
                val map = exampleContentRes.getOrNull() ?: return@launch
                Timber.d("Map $map")
                val list = map.content?.parseToObjectList<Map<String, Any>>("examples")
                val banner = list?.get(0)?.parseToObject<Banner>()
                val slides = list?.get(1)?.parseToObjectList<ImageSlide>("slides")
                val text = list?.get(2)?.parseToObject<Text>()
                val video = list?.get(3)?.parseToObject<Video>("video")
                Timber.d("Parsed video $video")

                exampleScreens.add(
                    Screen.MultiContentExampleScreen(
                        banner = banner,
                        slides = slides,
                        text = text?.text,
                        video = video
                    )
                )
            }

            val filterableRes =
                ContentClient.getInstance().filterContent(
                    FilterBy(
                        "/_meta/schema",
                        "https://example.com/blog-post-filter-and-sort"
                    )
                )
            if (filterableRes.isSuccess) {
                val results = filterableRes.getOrNull()
                Timber.d("Filterable map $results")
                val blogList =
                    results?.responses?.mapNotNull { it.content?.parseToObject<BlogPost>() }
                Timber.d("Blog list $blogList")

                if (blogList != null) {
                    exampleScreens.add(
                        Screen.BlogPostMenuScreen(
                            posts = blogList.map {
                                Screen.BlogPostScreen(it)
                            }
                        )
                    )
                }
            }

            val filterableByReadTimeRes =
                ContentClient.getInstance().filterContent(
                    FilterBy(
                        "/_meta/schema",
                        "https://example.com/blog-post-filter-and-sort"
                    ),
                    sortBy = SortBy("readTime", SortBy.Order.DESC)
                )
            if (filterableByReadTimeRes.isSuccess) {
                val results = filterableByReadTimeRes.getOrNull()
                val blogList =
                    results?.responses?.mapNotNull { it.content?.parseToObject<BlogPost>() }

                if (blogList != null) {
                    exampleScreens.add(
                        Screen.BlogPostMenuScreen(
                            posts = blogList.map {
                                Screen.BlogPostScreen(it)
                            },
                            name = "Blog menu sorted by read time",
                            id = "blog-list-by-read-time"
                        )
                    )
                }
            }

            val filterableByHomewareRes =
                ContentClient.getInstance().filterContent(
                    FilterBy(
                        "/_meta/schema",
                        "https://example.com/blog-post-filter-and-sort"
                    ),
                    FilterBy(
                        "/category",
                        "Homewares"
                    )
                )
            if (filterableByHomewareRes.isSuccess) {
                val results = filterableByHomewareRes.getOrNull()
                val blogList =
                    results?.responses?.mapNotNull { it.content?.parseToObject<BlogPost>() }

                if (blogList != null) {
                    exampleScreens.add(
                        Screen.BlogPostMenuScreen(
                            posts = blogList.map {
                                Screen.BlogPostScreen(it)
                            },
                            name = "Blog menu (Homewares)",
                            id = "blog-list-by-homewares"
                        )
                    )
                }
            }

            this@MainViewModel.screens = exampleScreens
        }
    }
}
