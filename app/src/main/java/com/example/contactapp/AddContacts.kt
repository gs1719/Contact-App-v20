package com.example.contactapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.contactapp.databinding.ActivityAddContactsBinding
import com.example.contactapp.databinding.ActivityCustomAlertBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddContacts : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactsBinding
    private lateinit var binding1: ActivityCustomAlertBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityAddContactsBinding.inflate(layoutInflater)
        binding1 = ActivityCustomAlertBinding.inflate(layoutInflater)
        val view = binding.root
        val alertView = binding1.root
        setContentView(view)

//        Alert box
        dialog = Dialog(this)
        /*
        dialog.setContentView(R.layout.custom_dialogue)
       * if not using view binding can use this
       *
       * */
        dialog.setContentView(alertView)

        binding.btnAddContacts.setOnClickListener {
            val contactName = binding.tietContactName.text.toString()
            val contactNumber = binding.tietContactPhoneNumber.text.toString()

            val contact = Contacts(contactName, contactNumber)
//            Getting User Id from different Class
            val userId = intent.getStringExtra(MainActivity.KEY)

            databaseReference =
                FirebaseDatabase.getInstance().getReference("/Users/$userId/contacts")

//            checking for non empty values otherwise all contacts will be cleared
            if (contactName.isNotEmpty() && contactNumber.isNotEmpty()) {
                if (contactNumber.length < 10)
                    Toast.makeText(this, "Phone Number must have 10 Digits", Toast.LENGTH_SHORT)
                        .show()
                else {
                    databaseReference.child(contactNumber).setValue(contact).addOnSuccessListener {
                        binding.tietContactPhoneNumber.text?.clear()
                        binding.tietContactName.text?.clear()
//                showing custom Dialog Box
                        dialog.show()
//                Setting ok button of custom alert box
                        binding1.btnOk.setOnClickListener {
                            dialog.dismiss()
                        }
//                Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(this, "Firebase Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else
                Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show()
        }
    }
}