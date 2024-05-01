package com.example.hvportfolio3.ui.gallery

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hvportfolio3.R

class ImageAdapter(private var selectedImagePath: List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView

        val filename: TextView

        init {
            image = view.findViewById(R.id.itemImage)
            filename = view.findViewById(R.id.titleImage)
        }

    }

    //var selectedImagePath = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_image_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagePath = selectedImagePath[position]
        holder.image.setImageBitmap(BitmapFactory.decodeFile(imagePath))
        holder.filename.text = imagePath.split("/").last().toString()
    }

    override fun getItemCount(): Int {
        return selectedImagePath.size
    }

    fun addSelectedImages(images: List<String>) {
        selectedImagePath = images
        notifyDataSetChanged()
    }
}

