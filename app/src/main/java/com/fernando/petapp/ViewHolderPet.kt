package com.fernando.petapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fernando.petapp.databinding.ItemPetBinding
import com.squareup.picasso.Picasso

class ViewHolderPet(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemPetBinding.bind(view)
    fun bind(PetItem: Pets){

        var imagen = PetItem.imagenUrl ?: R.drawable.descarga

        Picasso.get()
            .load(imagen)
            .error(R.drawable.descarga)
            .into(binding.ivImagen)
        binding.tvNombre.text = PetItem.nombre
        binding.tvRaza.text = PetItem.raza
    }
}