package com.example.trajectoryolympiad.data.repositories

import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.data.network.Response
import com.example.trajectoryolympiad.data.network.api.ServicesApi
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(private val api: ServicesApi) : ServicesRepository {
    override suspend fun fetchServices(): Response<List<VKService>> {
        return try {
            val services = api.fetchServices().items
            Response.Success(services)
        } catch (ex: Exception) {
            Response.Error(ex)
        }
    }
}