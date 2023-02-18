package com.example.trajectoryolympiad.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.trajectoryolympiad.MainActivity
import com.example.trajectoryolympiad.R
import com.example.trajectoryolympiad.base.BaseFragment
import com.example.trajectoryolympiad.base.launchOnLifecycle
import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.data.network.Response
import com.example.trajectoryolympiad.databinding.FragmentServicesBinding
import com.example.trajectoryolympiad.ui.servicedetails.ServiceDetailsFragment
import com.example.trajectoryolympiad.ui.services.adapter.ServicesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ServicesFragment : BaseFragment<FragmentServicesBinding>() {
    private val viewModel by viewModels<ServicesViewModel>()
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentServicesBinding
        get() = FragmentServicesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).toolbarTitle =
            resources.getString(R.string.toolbar_title_services)
        init()
        setupList()

    }

    private fun init() {
        viewModel.fetchServices()
    }

    private fun setupList() {
        val adapter = ServicesAdapter(::onClick)
        binding.servicesList.adapter = adapter
        launchOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.services.collect { res ->
                when (res) {
                    is Response.Success -> adapter.update(res.data)
                    is Response.Error -> Toast.makeText(
                        requireActivity(),
                        "Что-то пошло не так!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private fun onClick(service: VKService) {
        navigateToServiceDetails(service)
    }

    private fun navigateToServiceDetails(service: VKService) {
        val fragment = ServiceDetailsFragment.newInstance(service)
        parentFragmentManager.commit{
            replace(R.id.nav_host_fragment, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}