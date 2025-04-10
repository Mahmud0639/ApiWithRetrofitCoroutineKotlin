package com.manuni.apiwithretrofitcrud.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.manuni.apiwithretrofitcrud.databinding.SampleUserBinding
import com.manuni.apiwithretrofitcrud.models.UserModel

class UserAdapter(var items:ArrayList<UserModel>):RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    //higher order function
    private lateinit var onItemClick:((UserModel)->Unit)
    private lateinit var onEditClick:((UserModel)->Unit)
    private lateinit var onDeleteClick:((UserModel)->Unit)

    fun setItemClick(action: (UserModel) ->Unit){
        onItemClick = action
    }

    fun setEditClick(action:(UserModel)->Unit){
        onEditClick = action
    }

    fun setDeleteClick(action:(UserModel)->Unit){
        onDeleteClick = action
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding:SampleUserBinding = SampleUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding


        binding.tvName.text = "${item.name}"
        binding.tvPhone.text = "${item.phoneNumber}"
    }
    inner class MyViewHolder(var binding:SampleUserBinding):ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
            binding.editBtn.setOnClickListener {
                onEditClick(items[adapterPosition])
            }

            binding.deleteBtn.setOnClickListener {
                onDeleteClick(items[adapterPosition])
            }
        }
    }
}