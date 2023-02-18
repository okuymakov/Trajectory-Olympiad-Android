package com.example.trajectoryolympiad.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val adapter by lazy { ServicesAdapter(::onClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).toolbarTitle =
            resources.getString(R.string.toolbar_title_services)
        init()
        setupSideEffects()

    }

    private fun init() {
        fetchServices()
        binding.servicesList.adapter = adapter
        binding.retryBtn.setOnClickListener {
            fetchServices()
        }
    }

    private fun fetchServices() {
        startLoading()
        viewModel.fetchServices()
    }

    private fun setupSideEffects() {
        launchOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.services.collect {
                stopLoading()
                render(it)
            }
        }
    }

    private fun render(response: Response<List<VKService>>) {
        when (response) {
            is Response.Success -> {
                showOnlyList()
                adapter.update(response.data)
            }
            is Response.NoInternetError -> {
                showOnlyErrorView(resources.getString(R.string.no_internet_error_msg))
            }
            is Response.ApiError, is Response.UnexpectedError -> {
                showOnlyErrorView(resources.getString(R.string.unexpected_error_msg))
            }

        }
    }

    private fun showOnlyErrorView(errorMsg: String) {
        binding.servicesList.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.errorMsg.text = errorMsg
    }

    private fun showOnlyList() {
        binding.servicesList.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
    }

    private fun startLoading() {
        binding.servicesList.visibility = View.GONE
        binding.errorView.visibility = View.GONE
        binding.loadingSpinner.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.loadingSpinner.visibility = View.GONE
    }

    private fun onClick(service: VKService) {
        navigateToServiceDetails(service)
    }

    private fun navigateToServiceDetails(service: VKService) {
        val fragment = ServiceDetailsFragment.newInstance(service)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}