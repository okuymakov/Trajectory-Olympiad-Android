package com.example.trajectoryolympiad.data.network.api

import com.example.trajectoryolympiad.data.models.VKServices
import retrofit2.http.GET

interface ServicesApi {

    @GET("semi-final-data.json")
    suspend fun fetchServices(): VKServices
}