package com.example.spoonacularchatbot.core.data.remote

import android.annotation.SuppressLint
import com.bumptech.glide.BuildConfig
import com.example.spoonacularchatbot.core.data.remote.rxerrorhandling.RxErrorHandlingCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {


    @SuppressLint("LogNotTimber")
    fun <S> createService(
        serviceClass: Class<S>
    ): S {

        val baseURL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/"

        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()

        httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            httpClient.addInterceptor(logging)
        }

        builder.baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient().withNullSerialization())
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())

        val headersInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url().newBuilder()
                .build()
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "3dca1f8cb3mshc883e879a62591ap17f44ajsn3bd4c7568c32")
                .addHeader("useQueryString", "true")
                .url(url)
                .build()
            chain.proceed(request)
        }

        httpClient.addInterceptor(headersInterceptor)

        builder.client(httpClient.build())
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }
}