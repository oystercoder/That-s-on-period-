package com.example.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blog.databinding.ActivitySigninBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.blog.Model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class SigninActivity : AppCompatActivity() {
    private val binding:ActivitySigninBinding by lazy{
        ActivitySigninBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var storage:FirebaseStorage
    private val PICK_IMAGE_REQEST=1
    private var imageUri:Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        //firebase initialization

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance("https://blog-65532-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage=FirebaseStorage.getInstance()
        //visibility

        val action=intent.getStringExtra("action")
        //for login
        if(action=="login")
        {
            binding.loginEmailAddress.visibility=View.VISIBLE
            binding.loginButton.visibility= View.VISIBLE
            binding.loginPassword.visibility=View.VISIBLE
            binding.registerButton.visibility=View.INVISIBLE
            binding.registerNewHere.visibility=View.INVISIBLE
            binding.registerEmail.visibility=View.GONE
            binding.registerPassword.visibility=View.GONE
            binding.cardView.visibility=View.GONE
            binding.registerName.visibility=View.GONE



        }else if(action=="register")
        {

            binding.registerNewHere.isEnabled=false
            binding.registerNewHere.alpha=0.5f
            binding.registerButton.setOnClickListener{
                val registerName=binding.registerName.text.toString()
                val registerEmail=binding.registerEmail.text.toString()
                val registerPassword=binding.registerPassword.text.toString()
                if(registerName.isEmpty()||registerEmail.isEmpty()||registerPassword.isEmpty())
                {
                    Toast.makeText(this,"Please Fill All The Details",Toast.LENGTH_SHORT).show()
                }else
                {


                    auth.createUserWithEmailAndPassword(registerEmail,registerPassword)
                        .addOnCompleteListener{task->
                            if(task.isSuccessful)
                        {
                            Toast.makeText(this,"Successfully registered",Toast.LENGTH_SHORT).show()
                                val user=auth.currentUser

                            user?.let {
                                val userReference = database.getReference("users")
                                val userId = user.uid
                                val userData = UserData(
                                    registerName,
                                    registerPassword
                                )
                                userReference.child(userId).setValue(userData)
                                    .addOnSuccessListener {
                                        Log.d("TAG", "onCreate:data saved")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(
                                            "TAG",
                                            "onCreate:Error sad life android is worst!!!!! ${e.message}"
                                        )
                                    }
                                //upload image to firebase
                                val storageReference =
                                    storage.reference.child("profile_image/$userId.jpg")
                                storageReference.putFile(imageUri!!)
                                Toast.makeText(this, "success in registration", Toast.LENGTH_SHORT)
                                    .show()

                            }





                        }else{
                            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()


                        }
                        }
                }


            }
            binding.loginButton.isEnabled=false
            binding.loginButton.alpha=0.4f



        }

        //seton clicklistener for image
        binding.cardView.setOnClickListener{
            val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"select image"),PICK_IMAGE_REQEST)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_REQEST && resultCode== RESULT_OK && data!=null && data.data!=null)
        {
            imageUri=data.data

            Glide.with(this)
                .load(imageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imageView)
        }
    }


}
