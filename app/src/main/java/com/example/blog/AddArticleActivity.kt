package com.example.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.blog.Model.BlogItemModel
import com.example.blog.databinding.ActivityAddArticleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.blog.Model.UserData



class AddArticleActivity : AppCompatActivity() {
    private val binding:ActivityAddArticleBinding by lazy {
        ActivityAddArticleBinding.inflate(layoutInflater)
    }
    private val databaseReference:DatabaseReference=FirebaseDatabase.getInstance("https://blog-65532-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("blogs")
    private val userReference:DatabaseReference=FirebaseDatabase.getInstance("https://blog-65532-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
    private val auth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imageButton.setOnClickListener{
            finish()
        }
        binding.addBlogButton.setOnClickListener {
            val title=binding.blogTitle.editText?.text.toString().trim()
            val description=binding.blogDescription.editText?.text.toString().trim()
            if(title.isEmpty()||description.isEmpty())
            {
                Toast.makeText(this,"Aree Don't post blank posts😒",Toast.LENGTH_SHORT).show()

            }
            //get current user
            val user:FirebaseUser?=auth.currentUser
            if(user!=null)
            {
                val userId=user.uid;
                val userName=user.displayName?:"Anonymous"
                val userImageUrl=user.photoUrl?:""
                //fetch username and userprofile from database
                userReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var userData=snapshot.getValue(UserData::class.java)
                        if(userData!=null)
                        {
                            val userNameFromDB=userData.name
                            val userImageurlFromDB=userData.profileImage

                            //create a blogitem model
                            val blogItem=BlogItemModel(
                                title,

                                description,
                                likeCount=0,
                                userImageurlFromDB
                            )
                            //generate a unique key
                            val key=databaseReference.push().key
                            if(key!=null)
                            {
                                val blogReference=databaseReference.child(key)
                                blogReference.setValue(blogItem).addOnCompleteListener{
                                    if(it.isSuccessful)
                                    {
                                        finish()

                                    }
                                    else{
                                        Toast.makeText(this@AddArticleActivity,"Failed to add",Toast.LENGTH_SHORT).show()


                                    }
                                }
                            }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }

        }

    }
}