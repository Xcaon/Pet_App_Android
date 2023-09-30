package com.fernando.petapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterPet (private val listaPets: MutableList<Pets>) : RecyclerView.Adapter<ViewHolderPet>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPet {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderPet(layoutInflater.inflate(R.layout.item_pet, parent, false))
    }



    override fun onBindViewHolder(holder: ViewHolderPet, position: Int) {
        val item = listaPets[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listaPets.size
    }

}