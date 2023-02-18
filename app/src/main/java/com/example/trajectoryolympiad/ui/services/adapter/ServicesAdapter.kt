package com.example.trajectoryolympiad.ui.services.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trajectoryolympiad.data.models.VKService
import com.example.trajectoryolympiad.databinding.ServiceItemBinding

class ServicesAdapter(private val onClick: (VKService) -> Unit) :
    RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {

    private val data = mutableListOf<VKService>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newData: List<VKService>) {
        data.clear()
        data += newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ServiceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount() = data.size

    class ServiceViewHolder(binding: ServiceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val serviceName: TextView
        private val serviceIcon: ImageView

        init {
            serviceName = binding.serviceName
            serviceIcon = binding.serviceIcon
        }

        fun bind(service: VKService) {
            serviceName.text = service.name
            Glide.with(itemView.context).load(service.iconUrl).into(serviceIcon)

        }
    }
}