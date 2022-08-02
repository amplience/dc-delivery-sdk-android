# Amplience

Amplience is an Android library for use with the Amplience website.

## Installation

Gradle:

```gradle
dependencies {
    implementation '......'
}
```

## Getting started

### Initialising and accessing the client

Initialise the content client in your application class:

```kotlin
override fun onCreate() {
    super.onCreate()

    // The hub name can be found in your Amplience url https://[your-hub-name].cdn.content.amplience.net/
    ContentClient.initialise(context = applicationContext, hub = "your-hub-name")
    ...
}
```

then access the content client in your project with
```kotlin
val contentClient: ContentClient = ContentClient.getInstance()
```

Calling `getInstance()` before initialising the client will throw an exception.

### Customising the client 

Add customisation in initialisation with the `ContentClient.Configuration` class:

```kotlin
ContentClient.initialise(
    context = applicationContext, 
    hub = "your-hub-name",
    configuration = ContentClient.Configuration(
        freshApiKey = "..."
    )
)
```

### Getting a new client

If you need a new client separate from the one found in `getInstance()`, use `newInstance()` with the same parameters in `initialise()`. This instance will not be accessible through `getInstance()` so make sure to keep your own reference to it.

```kotlin
val client = ContentClient.newInstance(context = context, hub = "your-hub-name")
```

## Getting content (Kotlin coroutines)

These methods use Kotlin coroutines and should be called in a lifecycleScope

### Get content by key

This example uses [this banner example](https://ampproduct-doc.cdn.content.amplience.net/content/key/new-banner-format?depth=all&format=inlined)

```kotlin
val contentResult = ContentClient.getInstance().getContentByKey("new-banner-format")
if (contentResult.isSuccess) {
    val result: ListContentResponse = contentResult.getOrNull() ?: return@launch
    ...
} else {
    val exception = contentResult.exceptionOrNull()
    // Handle exception...
}
```

### Get content by id

This example uses [this slides example](https://ampproduct-doc.cdn.content.amplience.net/content/id/bd89c2ed-0ed5-4304-8c89-c0710af500e2?depth=all&format=inlined)

```kotlin
val slidesRes = ContentClient.getInstance().getContentById("bd89c2ed-0ed5-4304-8c89-c0710af500e2")
if (slidesRes.isSuccess) {
    val result: ListContentResponse = slidesRes.getOrNull() ?: return@launch
    ...
} else {
    val exception = slidesRes.exceptionOrNull()
    // Handle exception
}
```

### Get filtered content

#### One filter

```kotlin
val filterableRes = ContentClient.getInstance().filterContent(
    FilterBy(
        path = "/_meta/schema",
        value = "https://example.com/blog-post-filter-and-sort"
    )
)
if (filterableRes.isSuccess) {
    val results: FilterContentResponse = filterableRes.getOrNull()
}
```

#### Multiple filters

```kotlin
ContentClient.getInstance().filterContent(
    FilterBy(
        path = "/_meta/schema",
        value = "https://example.com/blog-post-filter-and-sort"
    ),
    FilterBy(
        path = "/category",
        value = "Homewares"
    )
)
```

#### Filter with sort

```kotlin
ContentClient.getInstance().filterContent(
    FilterBy(
        path = "/_meta/schema",
        value = "https://example.com/blog-post-filter-and-sort"
    ),
    sortBy = SortBy(key = "readTime", order = SortBy.Order.DESC)
)
```

## Getting content (callbacks)

### Get content by key

This example uses [this banner example](https://ampproduct-doc.cdn.content.amplience.net/content/key/new-banner-format?depth=all&format=inlined)

```kotlin
ContentClient.getInstance().getContentByKey("new-banner-format", object : ContentCallback<ListContentResponse?> {
    override fun onSuccess(result: ListContentResponse?) {
        // Handle success...
    }

    override fun onError(exception: Exception) {
        // Handle exception...
    }
})
```

### Get content by id

This example uses [this slides example](https://ampproduct-doc.cdn.content.amplience.net/content/id/bd89c2ed-0ed5-4304-8c89-c0710af500e2?depth=all&format=inlined)

```kotlin
ContentClient.getInstance().getContentById("bd89c2ed-0ed5-4304-8c89-c0710af500e2", object : ContentCallback<ListContentResponse?> {
    override fun onSuccess(result: ListContentResponse?) {
        // Handle result...
    }

    override fun onError(exception: Exception) {
        // Handle exception...
    }
})
```

### Get filtered content

#### One filter

##### Kotlin 

```kotlin
ContentClient.getInstance().filterContent(
    FilterBy(
        "/_meta/schema",
        "https://example.com/blog-post-filter-and-sort"
    ),
    callback = object : ContentCallback<FilterContentResponse?> {
        override fun onSuccess(result: FilterContentResponse?) {
            // Handle result...
        }

        override fun onError(exception: Exception) {
            // Handle exception...
        }
    }
)
```

##### Java

In Java you must pass an array of FilterBy, even if you just have one
```java
ContentClient.getInstance().filterContent(
    new FilterBy[]{
        new FilterBy(
            "/_meta/schema",
            "https://example.com/blog-post-filter-and-sort"
        )
    },
    new ContentCallback<FilterContentResponse>() {
        @Override
        public void onSuccess(FilterContentResponse result) {
            // Handle result...
        }

        @Override
        public void onError(@NonNull Exception exception) {
            // Handle exception
        }
});
```

#### Multiple filters

```kotlin
ContentClient.getInstance().filterContent(
    FilterBy(
        path = "/_meta/schema",
        value = "https://example.com/blog-post-filter-and-sort"
    ),
    FilterBy(
        path = "/category",
        value = "Homewares"
    ),
    callback = object : ContentCallback<FilterContentResponse?> {
        override fun onSuccess(result: FilterContentResponse?) {
            // Handle result...
        }

        override fun onError(exception: Exception) {
            // Handle exception...
        }
    }
)
```

#### Filter with sort

```kotlin
ContentClient.getInstance().filterContent(
    FilterBy(
        path = "/_meta/schema",
        value = "https://example.com/blog-post-filter-and-sort"
    ),
    sortBy = SortBy(key = "readTime", order = SortBy.Order.DESC),
    callback = object : ContentCallback<FilterContentResponse?> {
        override fun onSuccess(result: FilterContentResponse?) {
            // Handle result...
        }

        override fun onError(exception: Exception) {
            // Handle exception...
        }
    }
)
```

## Handling the response

[Example json response](https://ampproduct-doc.cdn.content.amplience.net/content/key/new-banner-format?depth=all&format=inlined)

Create a custom data class (or POJO) formatted the same as your content is returned
```kotlin
data class Banner(
    val background: Image,
    val headline: String,
    val strapline: String,
    val link: Link
)

data class Image(
    override val id: String,
    override val name: String,
    override val defaultHost: String,
    override val endpoint: String,
    val alt: String?
) : AmplienceImage()


data class Link(
    val title: String,
    val url: String
)
```
For more information on using images, look [here](link to images section of readme?)

Finally use utility method `parseToObject<*>()`
```kotlin
val result: ListContentResponse? = contentRes.getOrNull() // Your response from earlier
val banner: Banner? = result?.content?.parseToObject<Banner>()
```

Or in java use `parseToObject(Map<String, ?> map, Class<T> classOfT)`
```java
Banner banner = ParseUtils.parseToObject(result.getContent(), Banner.class);
```

If your data is returned in a list format you can use `parseToObjectList<*>()` instead:

[Example list response](https://ampproduct-doc.cdn.content.amplience.net/content/id/bd89c2ed-0ed5-4304-8c89-c0710af500e2?depth=all&format=inlined)

Kotlin data class
```kotlin
data class Slide(
    val headline: String,
    val subheading: String,
    val imageItem: ImageItem
)
```

To parse the list data:
```kotlin
val result = contentRes.getOrNull()
val slides: List<Slide>? = result?.content?.parseToObjectList<Slide>("slides")
```

Or in java:
```java
List<Slide> slides = ParseUtils.parseToObjectList(result.getContent(), Slide.class);
```

## Images

To use the Amplience image methods, create your custom Image data class as a subclass of AmplienceImage

```kotlin
data class Image(
    override val id: String,
    override val name: String,
    override val defaultHost: String,
    override val endpoint: String
) : AmplienceImage()
```

Do not use `AmplienceImage` directly because it is an abstract class and will cause errors.

### Image url

To get the default image url simply call

```kotlin
val url = image.getUrl()
```

The url can be customised with the `ImageUrlBuilder` class, for example to set a custom width and height to be returned

```kotlin
val url = image.getUrl {
    height(200)
    width(200)
}
```

Or in Java:
```java
String url = image.getUrl(
    (builder) -> {
        builder.height(200);
        builder.width(300);
        return Unit.INSTANCE;
    }
);
```

## Videos

In the same way as images, define your custom Video class as a subclass of `AmplienceVideo`

```kotlin
data class Video(
    override val id: String,
    override val name: String,
    override val endpoint: String,
    override val defaultHost: String
) : AmplienceVideo()
```

Do not use `AmplienceVideo` directly because it is an abstract class and will cause errors.

### Video url

```kotlin
val url = video.getUrl()
```

Optionally add a video profile (defaults to mp4_720p)

```kotlin
val url = video.getUrl(videoProfile = "custom_profile")
```

### Thumbnail url

```kotlin
val url = video.getThumbnailUrl()
```

This can be customised the same way as standard images with the `ImageUrlBuilder`.

You can optionally add a thumb name as defined on the backend system

```kotlin
val url = video.getThumbnailUrl(thumbname = "frame_0020.png")
```

## Fresh api

Use a fresh api to get non-cached data (for development only - see https://amplience.com/docs/development/freshapi/fresh-api.html for details)

### Set fresh state

Ensure you have set a `freshApiKey` in the `ContentClient.Configuration` class in either `initialise` or `newInstance`. Failing to set a fresh api key will throw a RuntimeException when trying to set fresh to true. 

```kotlin
ContentClient.getInstance().isFresh = true
```

### Check fresh state

```kotlin
val isFresh = ContentClient.getInstance().isFresh
```
