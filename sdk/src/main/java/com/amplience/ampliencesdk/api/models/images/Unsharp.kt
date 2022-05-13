package com.amplience.ampliencesdk.api.models.images

/**
 * @param radius - Gaussian filter radius.(0 – 5), if 0 the system will select an appropriate value.
 * @param sigma - Gaussian standard deviation. (0.01 – 5)
 * @param amount - The percentage difference. Can be greater than 100%.(1-300)
 * @param threshold - The threshold used to changed the brightness levels (0 to 255)
 */
data class Unsharp(
    val radius: Int, //  0-5
    val sigma: Double,
    val amount: Int,
    val threshold: Int
)
