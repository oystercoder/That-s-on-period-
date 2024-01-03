package com.example.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blog.databinding.ActivityMainBinding
import android.content.Intent
import com.example.blog.Model.BlogItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        databaseReference=FirebaseDatabase.getInstance().reference.child("blogs")
        if(userId!=null)
        {
            loadUserProfileImage(userId)
        }

       binding.floatingActionButton.setOnClickListener {
           startActivity(Intent(this,AddArticleActivity::class.java))

       }
    }

    private fun loadUserProfileImage(userId: String) {
        val userReference=FirebaseDatabase.getInstance().reference.child("users").child(userId)
        userReference.child("profileImage").addValueEventListener(object: ValueEventListner{})

    }
}