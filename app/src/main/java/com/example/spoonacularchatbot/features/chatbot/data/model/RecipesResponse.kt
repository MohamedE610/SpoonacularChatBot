package com.example.spoonacularchatbot.features.chatbot.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipesResponse(
    @Json(name = "baseUri")
    val baseUri: String = "",
    @Json(name = "expires")
    val expires: Long = 0,
    @Json(name = "number")
    val number: Int = 0,
    @Json(name = "offset")
    val offset: Int = 0,
    @Json(name = "processingTimeMs")
    val processingTimeMs: Int = 0,
    @Json(name = "results")
    val recipes: List<Recipe> = listOf(),
    @Json(name = "totalResults")
    val totalResults: Int = 0
) {
    @JsonClass(generateAdapter = true)
    data class Recipe(
        @Json(name = "id")
        val id: Int = 0,
        @Json(name = "image")
        val image: String = "",
        @Json(name = "openLicense")
        val openLicense: Int = 0,
        @Json(name = "readyInMinutes")
        val readyInMinutes: Int = 0,
        @Json(name = "servings")
        val servings: Int = 0,
        @Json(name = "sourceUrl")
        val sourceUrl: String = "",
        @Json(name = "title")
        val title: String = ""
    )
}