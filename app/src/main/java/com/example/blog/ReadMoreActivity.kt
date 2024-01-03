package com.example.blog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.blog.Model.BlogItemModel
import com.example.blog.databinding.ActivityReadMoreBinding


class ReadMoreActivity : AppCompatActivity() {
    private lateinit var binding:ActivityReadMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityReadMoreBinding.inflate(layoutInflater)


        setContentView(binding.root)
//        binding.backButton.setOnClickListener{
//            finish()
//        }
        val blogs= intent.getParcelableExtra<BlogItemModel>("blogItem")
        if(blogs!=null)
        {
            //retrive user related data
            binding.titleText.text=blogs.heading
            binding.blogDescriptionTextView.text=blogs.post

        }
        else{
            Toast.makeText(this,"Failed to load",Toast.LENGTH_SHORT).show()

        }
    }
}