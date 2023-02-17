package com.example.trajectoryolympiad.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class VKService (
    val name: String,
    val description: String,
    @SerialName("icon_url")
    val iconUrl: String,
    @SerialName("service_url")
    val serviceUrl: String,
)

@Serializable
data class VKServices(
    val items: List<VKService>
)