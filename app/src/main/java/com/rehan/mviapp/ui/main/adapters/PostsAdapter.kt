package com.rehan.mviapp.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rehan.mviapp.data.model.Posts
import com.rehan.mviapp.databinding.AdapterPostsItemsBinding

class PostsAdapter() : RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {

    var list = mutableListOf<Posts>()

    fun setPostsItems(list: List<Posts>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AdapterPostsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val posts = this.list[position]
        val binding = holder.binding

        binding.tvTitle.text = posts.q
        binding.tvDescription.text = posts.a
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: AdapterPostsItemsBinding) : RecyclerView.ViewHolder(binding.root)

}