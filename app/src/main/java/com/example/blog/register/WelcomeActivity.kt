package com.example.blog.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blog.databinding.ActivityWelcomeBinding
import android.content.Intent
import com.example.blog.MainActivity
import com.example.blog.SigninActivity


import com.example.blog.R
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    private val binding:ActivityWelcomeBinding by lazy{
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener{
            val intent=Intent(this,SigninActivity::class.java)
            intent.putExtra("action","login")
            startActivity(intent)
            finish()

        }
        binding.registerButton.setOnClickListener{
            val intent=Intent(this,SigninActivity::class.java)
            intent.putExtra("action","register")
            startActivity(intent)
            finish()

        }

    }
//    override fun onStart(){
//        super.onStart()
//        val currentUser=auth.currentUser
//        if(currentUser!=null)
//        {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//
//    }
}