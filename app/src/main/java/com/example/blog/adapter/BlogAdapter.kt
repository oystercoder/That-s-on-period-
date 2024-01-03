package com.example.blog.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blog.Model.BlogItemModel
import com.example.blog.ReadMoreActivity
import com.example.blog.databinding.BlogItemBinding
import com.google.firebase.database.DatabaseReference


class BlogAdapter(private val items:List<BlogItemModel>):
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogAdapter.BlogViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=BlogItemBinding.inflate(inflater,parent,false)
        return BlogViewHolder(binding)


    }



    override fun onBindViewHolder(holder: BlogAdapter.BlogViewHolder, position: Int) {
        val blogItem=items[position]
        holder.bind(blogItem)
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
             //listener
             binding.root.setOnClickListener {
                 val context=binding.root.context
                 val intent=Intent(context,ReadMoreActivity::class.java)
                 intent.putExtra("blogItem",blogItemModel);
                 context.startActivity(intent)
             }
         }

     }
    val postLikeReference=databaseReference.child("blogs").child("postId").child("likes")
    val currentUserLiked:Firebase?=currentUser?.uid.let{uid->{
        postLikedReference.child(uid).addListenerForSingleValueEvent(object:ValueEvent)
    }}





}