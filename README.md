[![Amplience Dynamic Content](https://github.com/amplience/dc-delivery-sdk-js/raw/master/media/header.png)](https://amplience.com/dynamic-content)

# amplience-sdk-android

> Official Android SDK for the Amplience Dynamic Content Delivery API, written in Kotlin

This SDK is designed to help build client side and server side content managed applications.

## Installation

Gradle:

```gradle
allprojects {
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    implementation 'com.github.amplience:dc-delivery-sdk-android:1.0'
}
```

## Features

- Fetch content and slots using [Content Delivery 1](https://docs.amplience.net/integration/deliveryapi.html#the-content-delivery-api) or [Content Delivery 2](https://docs.amplience.net/development/contentdelivery/readme.html)
- Fetch fresh content and slots for use with SSG build tools using the [Fresh API](https://amplience.com/docs/development/freshapi/fresh-api.html)
- Fetch preview content using Virtual Staging
- Transform content using the [Content Rendering Service](https://docs.amplience.net/integration/contentrenderingservice.html#the-content-rendering-service)
- Transform images on the fly using the [Dynamic Media Service](http://playground.amplience.com/di/app/#/intro)
- Filter Content Items using the [FilterBy](https://amplience.com/docs/development/contentdelivery/filterandsort.html) endpoint

# Requirements
- Android 6.0+ (Api 23)

## Usage

Initialise the SDK:

```kotlin
// The hub name can be found in your delivery url: https://[your-hub-name].cdn.content.amplience.net/
// It is also available in the "settings" menu in Dynamic Content.
ContentClient.initialise(context = applicationContext, hub = "your-hub-name")
```

then access the content client in your project with
```kotlin
val contentClient: ContentClient = ContentClient.getInstance()
```

Note: calling `getInstance()` before initialising the client will throw an exception.

If you need a new client separate from the one in `getInstance()`, use `newInstance()`. This instance will not be accessible through `getInstance()` so make sure to keep your own reference to it.

```kotlin
val client = ContentClient.newInstance(context = context, hub = "your-hub-name")
```

### Configuration

Add customisation in initialisation with the `ContentClient.Configuration` class:

| Option             | Description                                                                                                                                                                 |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| hub            | Content Delivery 2 API - Required\* - hubName to retrieve content from - [finding the hub name](https://docs.amplience.net/development/contentdelivery/readme.html#hubname) |
| isFresh             | Fresh API - toggle on/off the Fresh API                                                                                                                                                                |

```kotlin
ContentClient.initialise(
    context = applicationContext,
    hub = "your-hub-name",
    configuration = ContentClient.Configuration(
        freshApiKey = "..."
    )
)
```

### Get content by id

These methods use [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) and should be called in `LifecycleScope`. There are alternative methods that take a callback parameter if you are using Java, or are not using coroutines.

The `getContentById(id: String)` method returns a `Result<ListContentResponse>` which will resolve to the JSON of your slot or content item. If no content is found with the provided ID then the `result.isSuccess` will be false, and the error can be retrieved with `.exceptionOrNull()`.

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

The format of the content object will be specific to your content types, which define the JSON structure of content items and slots.

### Fetch content by delivery key

Once you have [set a delivery key for a slot or content item](https://docs.amplience.net/development/delivery-keys/readme.html), the content item must be published before it can be retrieved using this SDK.

The `getContentBykey(key: String)` method returns a `Result<ListContentResponse>` which will resolve to the JSON of your slot or content item. If no content is found with the provided key then the `result.isSuccess` will be false, and the error can be retrieved with `.exceptionOrNull()`.


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

The format of the content object will be specific to your content types, which define the JSON structure of content items and slots.

### Filtering Content Items

Content can be filtered and sorted using the `filterContent()` method.

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

This method accepts any number of `FilterBy`-type filters in a logical AND chaining (in Java this would be passed as a FilterBy array).

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

The model also allows for an optional `sortBy` which allows for sorting by `ASC` or `DESC` and an optional `page` key, a `Page` class instance. The `Page` class allows for both `size` and `cursor` to be specified for pagination control.

```kotlin
ContentClient.getInstance().filterContent(
    FilterBy(
        path = "/_meta/schema",
        value = "https://example.com/blog-post-filter-and-sort"
    ),
    sortBy = SortBy("readTime", SortBy.Order.DESC),
    page = FilterContentRequest.Page(size = 10)
)
```

### Fetching multiple Content Items in a single request

#### By IDs

Fetch multiple items by their delivery IDs e.g.,

```kotlin
ContentClient.getInstance().listContentById(
    "d6ccc158-6ab7-48d0-aa85-d9fbf2aef000",
    "b322f84a-9719-42ff-a6a0-6e2924608d19"
)
```

#### By keys

Fetching multiple items by their keys:

```kotlin
ContentClient.getInstance().listContentByKey(
    "blog/article-1",
    "blog/article-2"
)
```

## Getting content using callbacks

Here is an example of the callback strategy for retrieving content by key:

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

and by ID:

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

or using filters:

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

for multiple items:

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

and with parameters:

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

##### Java

To filter content in Java, pass an array of FilterBy objects:

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

## Handling the response

Here is an [example of a json response](https://ampproduct-doc.cdn.content.amplience.net/content/key/new-banner-format?depth=all&format=inlined).

Create a custom data class (or POJO) that is structured in the same way as your content is returned:

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

Finally use the utility method `parseToObject<*>()`:

```kotlin
val result: ListContentResponse? = contentRes.getOrNull() // Your response from earlier
val banner: Banner? = result?.content?.parseToObject<Banner>()
```

Or in Java, use `parseToObject(Map<String, ?> map, Class<T> classOfT)`

```java
Banner banner = ParseUtils.parseToObject(result.getContent(), Banner.class);
```

If your data is returned in a list format you can use `parseToObjectList<*>()` instead:

Kotlin data class
```kotlin
data class Slide(
    val headline: String,
    val subheading: String,
    val imageItem: ImageItem
)
```

See the [example list response](https://ampproduct-doc.cdn.content.amplience.net/content/id/bd89c2ed-0ed5-4304-8c89-c0710af500e2?depth=all&format=inlined) for the corresponding JSON.

To parse the list data:

```kotlin
val result = contentRes.getOrNull()
val slides: List<Slide>? = result?.content?.parseToObjectList<Slide>("slides")
```

Or in Java:

```java
List<Slide> slides = ParseUtils.parseToObjectList(result.getContent(), Slide.class);
```

## Images

To use Dynamic Media, create your custom Image data class as a subclass of `AmplienceImage`:

```kotlin
data class Image(
    override val id: String,
    override val name: String,
    override val defaultHost: String,
    override val endpoint: String
) : AmplienceImage()
```

Do not use `AmplienceImage` directly because it is an abstract class and will cause errors.

To get the default image url simply call

```kotlin
val url = image.getUrl()
```

The url can be customised with the `ImageUrlBuilder` class, for example to set a custom width and height to be returned:

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

In the same way as images, define your custom `Video` class as a subclass of `AmplienceVideo`

```kotlin
data class Video(
    override val id: String,
    override val name: String,
    override val endpoint: String,
    override val defaultHost: String
) : AmplienceVideo()
```

Do not use `AmplienceVideo` directly because it is an abstract class and will cause errors.

Get the video url:

```kotlin
val url = video.getUrl()
```

Optionally add a video profile (defaults to `mp4_720p`)

```kotlin
val url = video.getUrl(videoProfile = "custom_profile")
```

Get the thumnail image url:

```kotlin
val url = video.getThumbnailUrl()
```

This can be customised the same way as standard images with the `ImageUrlBuilder`.

You can optionally add a `thumbname` as defined on the backend system

```kotlin
val url = video.getThumbnailUrl(thumbname = "frame_0020.png")
```

### Fresh api

Use a fresh api to get non-cached data (for development only - see https://amplience.com/docs/development/freshapi/fresh-api.html for details)

Ensure you have set a `freshApiKey` in the `ContentClient.Configuration` class in either `initialise` or `newInstance`. Failing to set a fresh api key will throw a RuntimeException when trying to set fresh to true.

```kotlin
ContentClient.getInstance().isFresh = true
```

### Preview staging content

By default, the content client will request content from the production content delivery services. When a user wants to preview content before it is published you can re-point the client to a virtual staging environment (VSE) and set the VSE's endpoint in `UserDefaults` in the `stagingEnvironment` key.

Dynamic Content generates a VSE for each user and typically passes the "stagingEnvironment" value into your application using an UserDefaults value on SDK Initialisation. This allows each user to effectively have their own staging environment which allows content producers to work in parallel.


## Documentation

Please use the following documentation resources to assist building your application:

- Dynamic Content Delivery API 2 [Reference documentation](https://amplience.com/docs/development/contentdelivery/readme.html)
- Dynamic Content Fresh API [Reference documentation](https://amplience.com/docs/development/contentdelivery/filterapiintro.html)
- Dynamic Content [User guide](https://docs.amplience.net/)

## Getting Help

If you need help using the SDK please reach out using one of the following channels:

- Ask a question on [StackOverflow](https://stackoverflow.com/) using the tag `amplience-dynamic-content`
- Open a support ticket with [Amplience Support](https://support.amplience.com/)
- Contact your [Amplience Customer Success](https://amplience.com/customer-success) representative
- If you have found a bug please report it by [opening an issue](https://github.com/amplience/dc-delivery-sdk-js/issues/new)

## License

This software is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0),

Copyright 2022 Amplience

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
