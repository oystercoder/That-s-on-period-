package com.example.blog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blog.Model.BlogItemModel
import com.example.blog.databinding.BlogItemBinding

class BlogAdapter(private val items:List<BlogItemModel>):
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogAdapter.BlogViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=BlogItemBinding.inflate(inflater,parent,false)
        return BlogViewHolder(binding)


    }



    override fun onBindViewHolder(holder: BlogAdapter.BlogViewHolder, position: Int) {
        holder.bind(items[position])
    }


    override fun getItemCount(): Int {
        return items.size
    }

     inner class BlogViewHolder(private val binding: BlogItemBinding):
         RecyclerView.ViewHolder(binding.root){
         fun bind(blogItemModel: BlogItemModel) {
             binding.heading.text=blogItemModel.heading
             Glide.with(binding.profile.context)
                 .load(blogItemModel.profileImage)
                 .into(binding.profile)
             binding.post.text=blogItemModel.post
             binding.likeCount.text=blogItemModel.likeCount.toString()
         }

     }

}