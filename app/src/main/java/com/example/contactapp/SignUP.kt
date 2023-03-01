package com.example.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.contactapp.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUP : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignUP.setOnClickListener {
            val name = binding.tietName.text.toString()
            val mail = binding.tietEmail.text.toString()
            val uniqueId = binding.tietUserName.text.toString()
            val password = binding.tietPassword.text.toString()

            if (name.isNotEmpty() && mail.isNotEmpty() && uniqueId.isNotEmpty() && password.isNotEmpty()) {
                val user = Users(name, mail, password, uniqueId)

                databaseReference = FirebaseDatabase.getInstance().getReference("Users")

                databaseReference.child(uniqueId).setValue(user).addOnSuccessListener {
                    binding.tietName.text?.clear()
                    binding.tietEmail.text?.clear()
                    binding.tietUserName.text?.clear()
                    binding.tietPassword.text?.clear()

                    Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(this, "Please Enter Details Properly", Toast.LENGTH_SHORT).show()
        }
        binding.tvsignIN.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}