package com.example.getrequest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getrequest.R
import com.example.getrequest.model.ImagesApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class ImagesAdapter (val context: Context?, private val searchedImages: ArrayList<ImagesApi>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Binding View here

        Picasso.get().load(searchedImages[position].thumbnail_link).into(holder.itemView.searchedImage)
    }

    override fun getItemCount(): Int {
        return searchedImages.size
    }

    class ViewHolder(v: View?) : RecyclerView.ViewHolder(v!!), View.OnClickListener{
        override fun onClick(v: View?){


            //Onclick function here

        }

        init {
            itemView.setOnClickListener(this)
        }

        val searchedImage = itemView.searchedImage!!
    }


}