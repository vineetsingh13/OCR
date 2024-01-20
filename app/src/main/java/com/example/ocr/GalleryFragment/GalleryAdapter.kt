package com.example.ocr.GalleryFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ocr.R

class GalleryAdapter(private val imagePath: ArrayList<String>): RecyclerView.Adapter<viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.gallery_image_view,parent,false)

        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return imagePath.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val imagePath=imagePath[position]


        Glide.with(holder.itemView.context).load(imagePath).into(holder.imageView)
    }


}


class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val imageView: ImageView=itemView.findViewById(R.id.imageView)
}