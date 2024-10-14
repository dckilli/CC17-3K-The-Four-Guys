package com.example.myapplication3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myapplication3.models.User

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var username: EditText
    private lateinit var dob: EditText
    private lateinit var contact: EditText
    private lateinit var gender: RadioButton
    private lateinit var confirm_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val userID = auth.currentUser?.uid
        if (userID != null){
            fetchUserDetails(userID)
        }

        confirm_btn.setOnClickListener{
            val username = username.text.toString()
            val dob = dob.text.toString()
            val contact = contact.text.toString()
            val gender = gender.text.toString()

            if (username.isNotEmpty()){
                updateUser(userID!!, username, dob, contact, gender)
            }
        }
    }

    private fun fetchUserDetails(userID: String){
        firestore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { document ->
                if (document != null){
                    val user = document.toObject(User::class.java)
                    if (user != null){
                        username.setText(user.username)
                        dob.setText(user.dob)
                        contact.setText(user.contact)
                        gender.setText(user.gender)
                    }
                }
            }
            .addOnFailureListener{
                // Handle error
                Toast.makeText(this, "Error fetching user details", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUser(userID: String, username: String, dob: String, contact: String, gender: String){
        val userUpdates = mapOf(
            "username" to username,
            "dob" to dob,
            "contact" to contact,
            "gender" to gender
        )

        firestore.collection("users").document(userID)
            .update(userUpdates)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show()
            }
    }

}