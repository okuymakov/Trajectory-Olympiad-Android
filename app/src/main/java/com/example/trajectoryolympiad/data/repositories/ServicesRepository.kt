package com.example.trajectoryolympiad.data.repositories

import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.data.network.Response

interface ServicesRepository {
    suspend fun fetchServices(): Response<List<VKService>>
}