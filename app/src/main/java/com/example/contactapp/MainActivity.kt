package com.example.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.contactapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference


    companion object{
        const val KEY = "com.example.contactapp.MainActivity.KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.cirLoginButton.setOnClickListener {
            val uniqueId = binding.editTextUserName.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (uniqueId.isNotEmpty() && password.isNotEmpty()) {
                readData(uniqueId, password)
            } else {
                Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvsignUP.setOnClickListener {
//            Toast.makeText(this, "mesaaage", Toast.LENGTH_SHORT).show()
            intent = Intent(this, SignUP::class.java)
            startActivity(intent)
        }
    }

    private fun readData(uniqueId: String, pass: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(uniqueId).get().addOnSuccessListener {

            if (it.exists() && (it.child("password").value == pass)) {
                /*
                * checking username and password from database
                * it denotes the base or uniqueId here
                * */
                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value

                intent = Intent(this, AddContacts::class.java).apply {
                    putExtra(KEY,uniqueId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Username Or Password Incorrect", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed Fetching Data from database", Toast.LENGTH_SHORT).show()
        }
    }
}