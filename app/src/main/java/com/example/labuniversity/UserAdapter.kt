package com.example.labuniversity

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labuniversity.databinding.UserItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class UserAdapter(val listener: Listener): RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var users = ArrayList<User>()

    class UserHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = UserItemBinding.bind(item)
        fun bind(user: User, listener: Listener) = with(binding){
            UserTextView.setText(user.login.toString())

            itemView.setOnClickListener(){
                 listener.onClick(user)
            }
        }

    }

    fun clearUsers(){
        users.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserHolder, position: Int) {
         holder.bind(user = users[position], listener)
    }

    override fun getItemCount(): Int {
       // println(users.size.toString() + "!!!!!!!!!!!!!!!!!")
        return users.size
    }

    fun addUser(user: User){
        users.add(user)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(user: User){}
    }
}