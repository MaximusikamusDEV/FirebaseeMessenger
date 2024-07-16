package com.example.labuniversity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labuniversity.databinding.MessagesItemBinding
import com.example.labuniversity.databinding.UserItemBinding

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageHolder>() {
    private var mess = ArrayList<Message>()

    class MessageHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = MessagesItemBinding.bind(item)
        fun bind(mess: Message) = with(binding){
            messageView.setText(mess.message.toString())
        }

    }

    fun clearMess(){
        mess.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.MessageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.messages_item, parent, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageHolder, position: Int) {
        holder.bind(mess[position])
    }

    override fun getItemCount(): Int {
        // println(users.size.toString() + "!!!!!!!!!!!!!!!!!")
        return mess.size
    }

    fun addMessage(mess: Message){
        this.mess.add(mess)
        notifyDataSetChanged()
    }
}