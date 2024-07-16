package com.example.labuniversity.Fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.ListFragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labuniversity.MainActivity
import com.example.labuniversity.MessagesActivity
import com.example.labuniversity.R
import com.example.labuniversity.User
import com.example.labuniversity.UserAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.values


class ContactsFragment : Fragment(), UserAdapter.Listener {


    private val TAG = "contactsFragment"
    private val adapter = UserAdapter(this)

    private lateinit var activityData: MainActivity

    private var database = Firebase.database
    private val myRef = database.getReference("Users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        adapter.clearUsers()

        activityData = context as MainActivity

        adapter.notifyDataSetChanged()
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        adapter.clearUsers()

        adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityData = context as MainActivity
    }

    fun setData(data: String){
        activityData.setData(data)
    }

    fun init(){
        val rc: RecyclerView = requireView().findViewById(R.id.RCViewContacts)
        rc.layoutManager = LinearLayoutManager(requireContext())
        rc.adapter = adapter

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                if(snapshot.exists()){

                    adapter.clearUsers()

                    for(UID in snapshot.children){

                        if(UID.key != FirebaseAuth.getInstance().uid.toString())
                        for(login in UID.children) {
                            if(login.key.toString() == "login") {
                                val user = User(login.value.toString())
                                adapter.addUser(user)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled")
            }
        })


    }

    override fun onClick(user: User) {
        setData(user.login.toString())
      //  println(user.login.toString() + "!!!")
       // println(user.login.toString() + " +++++++++++++++++++")


     //   val supportFragmentManager: FragmentManager?
/*
        requireActivity().supportFragmentManager.commit {
            add<MessagesFragment>(R.id.ContViewForMenu)
            setReorderingAllowed(true)
            addToBackStack("Second")
        }*/


       /* val newFragm = MessagesFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.ContViewForMenu, newFragm).commit()*/


    }





}