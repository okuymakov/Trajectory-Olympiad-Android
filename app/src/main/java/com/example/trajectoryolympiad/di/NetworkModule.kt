package com.example.trajectoryolympiad.di

import com.example.trajectoryolympiad.base.Constants
import com.example.trajectoryolympiad.data.network.api.ServicesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder().baseUrl(Constants.API_URL)
            .addConverterFactory(
                json.asConverterFactory(contentType)
            ).build()
    }

    @Provides
    fun provideServicesApi(retrofit: Retrofit): ServicesApi {
        return retrofit.create(ServicesApi::class.java)
    }
}