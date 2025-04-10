package com.manuni.apiwithretrofitcrud.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.manuni.apiwithretrofitcrud.databinding.PhotoSampleBinding
import com.manuni.apiwithretrofitcrud.models.fileupload.FileUploadModel
import com.manuni.apiwithretrofitcrud.networkservices.AllApi

class FileUploadsAdapter(var items:ArrayList<FileUploadModel>):RecyclerView.Adapter<FileUploadsAdapter.FileViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding:PhotoSampleBinding = PhotoSampleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding
        binding.userTitle.text = item.name
        binding.picName.text = item.title

        binding.imgUserUploaded.load(AllApi.BASEURL_IMG_LOAD+item.imageUrl)
    }


    inner class FileViewHolder(var binding: PhotoSampleBinding):ViewHolder(binding.root){

    }
}