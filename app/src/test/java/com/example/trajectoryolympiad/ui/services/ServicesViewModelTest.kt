package com.example.trajectoryolympiad.ui.services

import com.example.trajectoryolympiad.rules.TestDispatcherRule
import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.data.network.Response
import com.example.trajectoryolympiad.data.repositories.ServicesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class ServicesViewModelTest() {
    private lateinit var viewModel: ServicesViewModel

    @get:Rule
    var mainCoroutineRule = TestDispatcherRule()

    @Mock
    lateinit var repo: ServicesRepository

    @Before
    fun setup() {
        viewModel = ServicesViewModel(repo)
    }


    @Test
    fun `when success response should contains the same data`() = runTest {
        val services = listOf(
            VKService(
                "ВКонтакте",
                "Самая популярная соцсеть и первое суперприложение в Роcсии",
                "https://mobile-olympiad-trajectory.hb.bizmrg.com/logo-vk.png",
                "https://vk.com/"
            )
        )
        val res = Response.Success(services)
        Mockito.`when`(repo.fetchServices()).thenReturn(res)
        viewModel.fetchServices()
        Assert.assertEquals(res, viewModel.services.value)

    }

    @Test
    fun `when any error response should contains the same error`() = runTest {
        val res = Response.NoInternetError("No internet connection", IOException())
        Mockito.`when`(repo.fetchServices()).thenReturn(res)
        viewModel.fetchServices()
        Assert.assertEquals(res, viewModel.services.value)
    }
}