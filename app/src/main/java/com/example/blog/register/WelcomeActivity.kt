package com.example.blog.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blog.databinding.ActivityWelcomeBinding
import android.content.Intent
import com.example.blog.SigninActivity


import com.example.blog.R

class WelcomeActivity : AppCompatActivity() {
    private val binding:ActivityWelcomeBinding by lazy{
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener{
            val intent=Intent(this,SigninActivity::class.java)
            intent.putExtra("action","login")
            startActivity(intent)

        }
        binding.registerButton.setOnClickListener{
            val intent=Intent(this,SigninActivity::class.java)
            intent.putExtra("action","register")
            startActivity(intent)

        }

    }
}