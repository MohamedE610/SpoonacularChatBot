package com.example.spoonacularchatbot.features.chatbot.data.source.remote

import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import io.reactivex.Single
import retrofit2.http.*

interface SpoonacularApi {
    @GET("recipes/search")
    fun getRecipes(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String? = null,
        @Query("diet") diet: String? = null,
        @Query("excludeIngredients") excludeIngredients: String? = null,
        @Query("intolerances") intolerance: String? = null,
        @Query("number") number: Int = 5
    ): Single<RecipesResponse>

    @FormUrlEncoded
    @POST("food/detect")
    fun detectFoodInText(@Field("text") text: String): Single<FoodResponse>

}