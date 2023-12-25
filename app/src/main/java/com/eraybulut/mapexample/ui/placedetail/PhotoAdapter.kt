package com.eraybulut.mapexample.ui.placedetail

/**
 * Created by Eray BULUT on 23.12.2023
 * eraybulutlar@gmail.com
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eraybulut.mapexample.data.model.response.Photo
import com.eraybulut.mapexample.databinding.ItemPhotoBinding
import com.eraybulut.mapexample.util.extensions.loadUrl
import javax.inject.Inject

class PhotoAdapter @Inject constructor(): RecyclerView.Adapter<PhotoAdapter.CardViewHolder>() {

    private var photos = emptyList<Photo>()

    fun setPhotos(photos : List<Photo>){
        this.photos = photos
        notifyDataSetChanged()
    }

    inner class CardViewHolder(private val binding : ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photo :Photo){
            with(binding){
                icPhoto.loadUrl(photo.photoReference.orEmpty())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder = CardViewHolder(
        ItemPhotoBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(photo = photos[position])

    override fun getItemCount(): Int = photos.size
}