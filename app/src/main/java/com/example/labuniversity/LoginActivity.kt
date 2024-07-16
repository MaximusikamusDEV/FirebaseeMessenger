package com.example.labuniversity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.labuniversity.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth


        binding.LogButt.setOnClickListener()
        {

            val loginText = binding.editTextLogin.text.toString()
            val passwordText = binding.editTextPassword.text.toString()
            
            if(loginText.isNotEmpty() && passwordText.isNotEmpty()){
            
                FirebaseAuth.getInstance().signInWithEmailAndPassword(loginText, passwordText).addOnCompleteListener(){
                    if(it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                    } else
                        Toast.makeText(this, "Try Again or Register", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.ReggButt.setOnClickListener()
        {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }





        setContentView(binding.root)
    }
}