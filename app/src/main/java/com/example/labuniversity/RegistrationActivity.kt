package com.example.labuniversity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.labuniversity.databinding.ActivityRegistrationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegistrationBinding.inflate(layoutInflater)

        auth = Firebase.auth


        binding.LogButt.setOnClickListener(){

            val loginText = binding.editTextLogin.text.toString()
            val passwordText = binding.editTextPassword.text.toString()

            if(loginText.isNotEmpty() && passwordText.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(loginText, passwordText).addOnCompleteListener(){
                    if(it.isSuccessful){
                        startActivity(Intent(this, LoginActivity::class.java))
                        val UID: String = FirebaseAuth.getInstance().uid.toString()
                        addNewUserToDb(loginText, passwordText, UID)
                        finish()
                    } else
                        Toast.makeText(this, "This user is already exist. Try again", Toast.LENGTH_LONG).show()
                }
            }

        }

        binding.ReggButt.setOnClickListener(){
            startActivity(Intent(this, LoginActivity::class.java))
        }


        setContentView(binding.root)
    }

    fun addNewUserToDb(login: String, password: String, UID: String){
        val user = User(login, password)
        database.child("Users").child(UID).setValue(user)
        database.child("Messages").child(UID).setValue("")

        val myRef = Firebase.database.getReference("Users")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                if(snapshot.exists()){
                    for(UID2 in snapshot.children){

                        println(UID2.key.toString() + "_---------------------------------------")
                        database.child("Messages").child(UID).child(UID2.key.toString()).setValue("default")

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled")
            }
        })


        }

}