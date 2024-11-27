package com.example.myapplication3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import java.util.Calendar
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myapplication3.models.User

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val usernameEditText: EditText = findViewById(R.id.su_username)
        val emailEditText: EditText = findViewById(R.id.email)
        val dobEditText: EditText = findViewById(R.id.dob)
        val contactEditText: EditText = findViewById(R.id.contact)
        val passwordEditText: EditText = findViewById(R.id.su_password)
        val confirmPasswordEditText: EditText = findViewById(R.id.confirm_password)
        val genderRadioGroup: RadioGroup = findViewById(R.id.gender)
        val signupButton: Button = findViewById(R.id.register_btn)


        dobEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val DatePickerDialog = DatePickerDialog(this,
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    dobEditText.setText(selectedDate)
                }, year, month, day)
            DatePickerDialog.show()
        }

        contactEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (!it.startsWith("+63")) {
                        contactEditText.removeTextChangedListener(this)  // Temporarily remove listener to prevent recursion
                        contactEditText.setText("+63")
                        contactEditText.setSelection(contactEditText.text.length)
                        contactEditText.addTextChangedListener(this)  // Add the listener back
                    } else if (it.length > 13) {
                        contactEditText.removeTextChangedListener(this)
                        contactEditText.setText(it.subSequence(0, 13))
                        contactEditText.setSelection(contactEditText.text.length)
                        contactEditText.addTextChangedListener(this)
                    }
                }
            }
        })

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val dob = dobEditText.text.toString()
            val contact = contactEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || email.isEmpty() || dob.isEmpty() || contact.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Show error message
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else if (password != confirmPassword) {
                // Show error message
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    val selectedGenderId = genderRadioGroup.checkedRadioButtonId
                    if (selectedGenderId == -1) {
                        Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val genderRadioButton: RadioButton = findViewById<RadioButton>(selectedGenderId)
                    val gender = genderRadioButton.text.toString()
                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
        }
    }

    private fun createUser(username: String, password: String, email: String, dob: String, contact: String, gender: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userID = auth.currentUser?.uid ?: ""
                    val user = User(
                        id = userID,
                        username = username,
                        password = password,
                        email = email,
                        dob = dob,
                        gender = gender,
                    )
                    saveUserToFirestore(user)
                }else{
                    Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun saveUserToFirestore(user: User){
        firestore.collection("users").document(user.id)
            .set(user)
            .addOnSuccessListener{
                Toast.makeText(this,"User signed up successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show()
            }
    }

}