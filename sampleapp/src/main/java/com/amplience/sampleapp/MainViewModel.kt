package com.amplience.sampleapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.api.models.FilterBy
import com.amplience.ampliencesdk.api.models.SortBy
import com.amplience.ampliencesdk.parseToObject
import com.amplience.ampliencesdk.parseToObjectList
import com.amplience.sampleapp.model.*
import kotlinx.coroutines.launch
import timber.log.Timber


class MainViewModel : ViewModel() {

    var appBarTitle by mutableStateOf("")
    var screens by mutableStateOf<List<Screen>>(listOf())
    var lowBandwidth: Boolean = false

    init {
        getExamples()
    }

    private fun getExamples() {
        viewModelScope.launch {
            val exampleScreens = arrayListOf<Screen>(Screen.HomeScreen())

            val bannerRes = AmplienceManager.getInstance().getContentByKey("new-banner-format")
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

            val exampleContentRes =
                AmplienceManager.getInstance().getContentByKey("example-content-items")
            if (exampleContentRes.isSuccess) {
                val map = exampleContentRes.getOrNull() ?: return@launch
                Timber.d("Map $map")
                val list = map.content.parseToObjectList<Map<String, Any>>("examples")
                val banner = list?.get(0)?.parseToObject<Banner>()
                val slides = list?.get(1)?.parseToObjectList<ImageSlide>("slides")
                val text = list?.get(2)?.parseToObject<Text>()

                if (banner != null && slides != null && text != null)
                    exampleScreens.add(
                        Screen.MultiContentExampleScreen(
                            banner = banner,
                            slides = slides,
                            text = text.text
                        )
                    )
            }

            val filterableRes =
                AmplienceManager.getInstance().getContentByFilters(
                    FilterBy(
                        "/_meta/schema",
                        "https://example.com/blog-post-filter-and-sort"
                    )
                )
            if (filterableRes.isSuccess) {
                val results = filterableRes.getOrNull()
                Timber.d("Filterable map $results")
                val blogList = results?.mapNotNull { it.content.parseToObject<BlogPost>() }
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
                AmplienceManager.getInstance().getContentByFilters(
                    FilterBy(
                        "/_meta/schema",
                        "https://example.com/blog-post-filter-and-sort"
                    ),
                    sortBy = SortBy("readTime", SortBy.Order.DESC)
                )
            if (filterableByReadTimeRes.isSuccess) {
                val results = filterableByReadTimeRes.getOrNull()
                val blogList = results?.mapNotNull { it.content.parseToObject<BlogPost>() }

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
                AmplienceManager.getInstance().getContentByFilters(
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
                val blogList = results?.mapNotNull { it.content.parseToObject<BlogPost>() }

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
