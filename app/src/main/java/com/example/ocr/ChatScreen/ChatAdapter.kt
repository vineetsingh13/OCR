package com.example.ocr.ChatScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ocr.databinding.ChatSectionBinding


class ChatAdapter(private val messageList: List<messageMap>): RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

        val binding=ChatSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return messageList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        val currentMessage = messageList[position]

        return holder.bindView(currentMessage)
    }

}

class ChatViewHolder(val binding: ChatSectionBinding): RecyclerView.ViewHolder(binding.root){

    var req=binding.request
    var res=binding.response

    fun bindView(messageMap: messageMap) {
        binding.request.text = messageMap.question
        binding.response.text = messageMap.response
    }
}