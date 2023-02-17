package com.example.trajectoryolympiad.ui.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.data.network.Response
import com.example.trajectoryolympiad.data.repositories.ServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(private val repo: ServicesRepository) : ViewModel() {
    private val _services =
        MutableStateFlow<Response<List<VKService>>>(Response.Success(emptyList()))
    val services = _services.asStateFlow()

    fun fetchServices() {
        viewModelScope.launch {
            val res = repo.fetchServices()
            _services.emit(res)
        }
    }
}