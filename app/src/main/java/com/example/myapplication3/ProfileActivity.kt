package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myapplication3.models.User

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userDOB: TextView
    private lateinit var userContact: TextView
    private lateinit var userGender: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val userID = auth.currentUser?.uid
        if (userID != null){
            fetchUserDetails(userID)
        }

        val editProfileBtn: Button = findViewById(R.id.editProfileBtn)
        editProfileBtn.setOnClickListener{
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fetchUserDetails(userID: String){
        firestore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { document ->
                if (document != null){
                    val user = document.toObject(User::class.java)
                    if (user != null){
                        userName.text = user.username
                        userEmail.text = user.email
                        userDOB.text = user.dob
                        userContact.text = user.contact
                        userGender.text = user.gender
                    }
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error fetching user details", Toast.LENGTH_SHORT).show()
            }
    }

}