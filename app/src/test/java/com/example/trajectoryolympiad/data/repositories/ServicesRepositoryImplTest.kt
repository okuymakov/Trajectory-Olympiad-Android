package com.example.trajectoryolympiad.data.repositories

import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.data.models.VKServices
import com.example.trajectoryolympiad.data.network.Response
import com.example.trajectoryolympiad.data.network.api.ServicesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doAnswer
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class ServicesRepositoryImplTest {
    private lateinit var repo: ServicesRepositoryImpl

    @Mock
    private lateinit var api: ServicesApi

    @Before
    fun setup() {
        repo = ServicesRepositoryImpl(api)
    }

    @Test
    fun `when success should return the same data`() = runTest {
        val services = VKServices(
            listOf(
                VKService(
                    "ВКонтакте",
                    "Самая популярная соцсеть и первое суперприложение в Роcсии",
                    "https://mobile-olympiad-trajectory.hb.bizmrg.com/logo-vk.png",
                    "https://vk.com/"
                )
            )
        )

        Mockito.`when`(api.fetchServices()).thenReturn(services)
        val res = repo.fetchServices()
        Assert.assertTrue(res is Response.Success)
        Assert.assertEquals((res as Response.Success).data, services.items)
    }

    @Test
    fun `when IOException should return NoInternetError`() = runTest {
        Mockito.`when`(api.fetchServices()).doAnswer { throw IOException() }
        val res = repo.fetchServices()
        Assert.assertTrue(res is Response.NoInternetError)
    }

    @Test
    fun `when HttpException should return NoInternetError`() = runTest {
        Mockito.`when`(api.fetchServices()).thenThrow(
            HttpException(
                retrofit2.Response.error<ResponseBody>(
                    400,
                    ResponseBody.create(MediaType.parse("plain/text"), "Bad request")
                )
            )
        )
        val res = repo.fetchServices()
        Assert.assertTrue(res is Response.ApiError)
    }

    @Test
    fun `when unknown exception should return UnexpectedError`() = runTest {
        Mockito.`when`(api.fetchServices()).doAnswer { throw Exception() }
        val res = repo.fetchServices()
        Assert.assertTrue(res is Response.UnexpectedError)
    }
}