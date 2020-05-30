package com.example.spoonacularchatbot.features.chatbot.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse(
    @Json(name = "annotations")
    val foods: List<Food> = listOf(),
    @Json(name = "processedInMs")
    val processedInMs: Int = 0
) {
    @JsonClass(generateAdapter = true)
    data class Food(
        @Json(name = "annotation")
        val annotation: String = "",
        @Json(name = "image")
        val image: String = "",
        @Json(name = "tag")
        val tag: String = ""
    )
}