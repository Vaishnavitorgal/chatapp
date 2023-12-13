package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
//            inflate receive
            val view : View = LayoutInflater.from(context).inflate(R.layout.recieve, parent,false)
            return ReceiveViewHolder(view)
        }
        else{
//            inflate sent
            val view : View = LayoutInflater.from(context).inflate(R.layout.sent, parent,false)
            return SentViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg = messageList[position]
        if(holder.javaClass==SentViewHolder::class.java){

            val viewHolder = holder as SentViewHolder //type cast
            holder.sentMsg.text=currentMsg.message
        }
        else{

            val viewHolder = holder as ReceiveViewHolder //type cast
            holder.receiveMsg.text=currentMsg.message
        }
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sentMsg = itemView.findViewById<TextView>(R.id.txt_sent_msg)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val receiveMsg = itemView.findViewById<TextView>(R.id.txt_receive_msg)
    }

}