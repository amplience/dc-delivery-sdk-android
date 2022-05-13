package com.amplience.ampliencesdk.api.models.images

sealed class ScaleMode {
    object CropToFit : ScaleMode()
    object Stretch : ScaleMode()
    object TopLeft : ScaleMode()
    object TopCenter : ScaleMode()
    object TopRight : ScaleMode()
    object MiddleLeft : ScaleMode()
    object MiddleCenter : ScaleMode() // Default
    object MiddleRight : ScaleMode()
    object BottomLeft : ScaleMode()
    object BottomCenter : ScaleMode()
    object BottomRight : ScaleMode()
    class AspectRatio(val ratio: String) : ScaleMode()
    class Edge(val type: EdgeType, val length: Int) : ScaleMode() {
        enum class EdgeType {
            Width, Height, Smallest, Largest;

            override fun toString(): String = when (this) {
                Width -> "w"
                Height -> "h"
                Smallest -> "smallest"
                Largest -> "largest"
            }
        }
    }
    class Clamp(val width: Int, val height: Int) : ScaleMode()

    override fun toString(): String = when (this) {
        CropToFit -> "c"
        Stretch -> "s"
        TopLeft -> "tl"
        TopCenter -> "tc"
        TopRight -> "tr"
        MiddleLeft -> "ml"
        MiddleCenter -> "mc"
        MiddleRight -> "mr"
        BottomLeft -> "bl"
        BottomCenter -> "bc"
        BottomRight -> "br"
        is AspectRatio -> "aspect&aspect=$ratio"
        is Edge -> "edge&resize.edge=$type&resize.edge.length=$length"
        is Clamp -> "clamp"
    }
}
