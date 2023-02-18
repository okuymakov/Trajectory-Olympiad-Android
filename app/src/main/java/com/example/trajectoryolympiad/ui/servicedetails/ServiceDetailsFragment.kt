package com.example.trajectoryolympiad.ui.servicedetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.trajectoryolympiad.MainActivity
import com.example.trajectoryolympiad.base.BaseFragment
import com.example.trajectoryolympiad.base.getObject
import com.example.trajectoryolympiad.base.load
import com.example.trajectoryolympiad.base.putObject
import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.databinding.FragmentServiceDetailsBinding


private const val ARG_SERVICE = "service"

class ServiceDetailsFragment : BaseFragment<FragmentServiceDetailsBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentServiceDetailsBinding
        get() = FragmentServiceDetailsBinding::inflate

    private var service: VKService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            service = it.getObject(ARG_SERVICE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).toolbarTitle = service!!.name
        init()

    }

    private fun init() {
        service?.run {
            binding.serviceIcon.load(iconUrl)
            binding.serviceName.text = name
            binding.serviceDescription.text = description
            binding.serviceUrl.text = serviceUrl

            binding.serviceUrl.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(serviceUrl))
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        fun newInstance(service: VKService) =
            ServiceDetailsFragment().apply {
                arguments = Bundle().apply {
                    putObject(ARG_SERVICE, service)
                }
            }
    }
}