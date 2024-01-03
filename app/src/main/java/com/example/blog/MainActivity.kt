package com.example.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blog.databinding.ActivityMainBinding
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.blog.Model.BlogItemModel
import com.example.blog.adapter.BlogAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)

    }
    private lateinit var  databaseReference:DatabaseReference
    private val blogItems=mutableListOf<BlogItemModel>()
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        val userId=auth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance("https://blog-65532-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("blogs")
        if(userId!=null)
        {
            loadUserProfileImage(userId)
        }

        //initialize the recycler view and set adapter
        val recyclerView=binding.blogRecyclerView
        val blogAdapter=BlogAdapter(blogItems)
        recyclerView.adapter=blogAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)
       // fetch data from firebase database
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                blogItems.clear()
                for(snapshot in snapshot.children)
                {
                    val blogItem=snapshot.getValue(BlogItemModel::class.java)
                    if(blogItem!=null)
                    {
                        blogItems.add(blogItem)
                    }

                }
                //notify the adapter that data has changed
                blogAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(this@MainActivity,"loading failed",Toast.LENGTH_SHORT).show()
            }

        })


       binding.floatingActionButton.setOnClickListener {
           startActivity(Intent(this,AddArticleActivity::class.java))

       }
    }

    private fun loadUserProfileImage(userId: String) {
        val userReference=FirebaseDatabase.getInstance().reference.child("users").child(userId)
        userReference.child("profileImage").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val profileImageUrl=snapshot.getValue(String::class.java)
                if(profileImageUrl!=null)
                {
                    Glide.with(this@MainActivity)
                        .load(profileImageUrl)
                        .into(binding.profileImage)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,"Error loading image",Toast.LENGTH_SHORT).show()

            }

        })

    }
}