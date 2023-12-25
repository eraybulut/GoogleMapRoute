package com.eraybulut.mapexample.ui.selectdestination

/**
 * Created by Eray BULUT on 23.12.2023
 * eraybulutlar@gmail.com
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eraybulut.mapexample.data.model.response.Prediction
import com.eraybulut.mapexample.databinding.ItemAddressBinding
import javax.inject.Inject

class AddressAdapter @Inject constructor(): RecyclerView.Adapter<AddressAdapter.CardViewHolder>() {

    private var onItemClickListener : ((String) -> Unit)? = null

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    private var address = emptyList<Prediction>()

    fun setAddress(address : List<Prediction>){
        this.address = address
        notifyDataSetChanged()
    }

    inner class CardViewHolder(private val binding : ItemAddressBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(address : Prediction){
            with(binding){
                txtAddress.text = address.description

                root.setOnClickListener {
                    onItemClickListener?.invoke(address.placeId.orEmpty())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder = CardViewHolder(
        ItemAddressBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(address = address[position])

    override fun getItemCount(): Int = address.size
}