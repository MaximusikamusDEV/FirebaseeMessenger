package com.example.labuniversity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labuniversity.databinding.ActivityMainBinding
import com.example.labuniversity.databinding.ActivityMessagesBinding
import com.example.labuniversity.databinding.MessagesItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.values
import com.google.firebase.database.values
import kotlin.math.log

class MessagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesBinding
    private val messAdapter = MessageAdapter()
    val database = Firebase.database.reference
    lateinit var name: String
    var loginU: String = " "
    var ID: String = " "
    var fl = false
    private var myRef = Firebase.database.getReference("Messages")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)

        name = intent.getStringExtra("Name").toString()

        binding.sendMessButt.setOnClickListener(){
            sendMessage(binding.MessageText.text.toString())
            binding.MessageText.setText("")
        }

        binding.ContactName.setText(name)
        init()
        println("init")
        sendMessageKost("")
        setContentView(binding.root)
    }

    fun init(){
        binding.apply {

            messRC.layoutManager = LinearLayoutManager(this@MessagesActivity)
            messRC.adapter = messAdapter




            myRef.child(FirebaseAuth.getInstance().uid.toString()).push()

                myRef.child(FirebaseAuth.getInstance().uid.toString()).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {


                        println("init " + ID + " " + loginU)

                        if (snapshot.exists()) {
                                    for (UID2 in snapshot.children) {
                                        if (UID2.key == ID) {
                                            messAdapter.clearMess()

                                            for (mess in UID2.children) {
                                                //messAdapter.clearMess()

                                                messAdapter.addMessage(
                                                    Message(
                                                        "me",
                                                        mess.value.toString()
                                                    )
                                                )


                                            }
                                        }
                                    }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(ContentValues.TAG, "loadPost:onCancelled")
                    }
                })

           // myRef.child(FirebaseAuth.getInstance().uid.toString()).push().setValue("default")

                val uRef = Firebase.database.getReference("Users")


                uRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {


                        if (snapshot.exists()) {
                            for (UID in snapshot.children) {


                                if (UID.key == FirebaseAuth.getInstance().uid.toString()) {
                                    for (login in UID.children) {
                                        if (login.key.toString() == "login") {

                                            loginU = login.value.toString()


                                        }
                                    }
                                }
                            }

                            for (UID in snapshot.children) {

                                for (login in UID.children) {
                                    // println(login.value.toString() + "}}}}}}}}}}}}}")
                                    if (login.key.toString() == "login" && login.value.toString() == name) {

                                        ID = UID.key.toString()


                                    }
                                }

                            }

                        }


                        if(ID == " ")
                            finish()


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(ContentValues.TAG, "loadPost:onCancelled")
                    }
                })


        }
    }

    fun sendMessage(message: String){
      //  messAdapter.clearMess()
       // val mess = loginU + ": " + message
        myRef.child(ID).child(FirebaseAuth.getInstance().uid.toString()).push()
            .setValue(loginU + ": " + message)
        ///  messAdapter.clearMess()
        myRef.child(FirebaseAuth.getInstance().uid.toString()).child(ID).push()
            .setValue(loginU + ": " + message)
        //messAdapter.addMessage(Message("me", mess))
       // messAdapter.clearMess()





       // println(ID + " }}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}")

//        val mess = Message(FirebaseAuth.getInstance().uid.toString(), message)

     //   val muid = myRef.push().key
     //   val mesRef = myRef.child(name)
     //   mesRef.push().key
     //   mesRef.setValue(message)
    }

    fun sendMessageKost(message: String){
        myRef.child(ID).child(FirebaseAuth.getInstance().uid.toString()).child("time").push()
            .setValue(loginU + ": " + message)
        myRef.child(FirebaseAuth.getInstance().uid.toString()).child("time").child(ID).push()
            .setValue(loginU + ": " + message)

        myRef.child(" ").child(FirebaseAuth.getInstance().uid.toString()).child("time")
            .setValue("")
        myRef.child(FirebaseAuth.getInstance().uid.toString()).child(ID).child("time")
            .setValue("")
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


}