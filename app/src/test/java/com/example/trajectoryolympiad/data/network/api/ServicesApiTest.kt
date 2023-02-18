package com.example.trajectoryolympiad.data.network.api

import com.example.trajectoryolympiad.base.Constants
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class ServicesApiTest {
    private lateinit var retrofit: Retrofit

    @Before
    fun setup() {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Test
    fun `should return the same api url`() {
        assert(retrofit.baseUrl().url().toString() == Constants.API_URL)
    }
}