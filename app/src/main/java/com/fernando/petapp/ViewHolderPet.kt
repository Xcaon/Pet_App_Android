package com.fernando.petapp

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fernando.petapp.databinding.ItemPetBinding
import com.squareup.picasso.Picasso

class ViewHolderPet(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemPetBinding.bind(view)
    fun bind(PetItem: Pet, OnItemSelected: (Int) -> Unit, OnLongItemSelected: (Int) -> Unit) {

        var imagen = PetItem.imagenUrl ?: R.drawable.descarga

        Picasso.get()
            .load(PetItem.imagenUrl.toString())
            .error(R.drawable.descarga)
            .into(binding.ivImagen)
        binding.tvNombre.text = PetItem.nombre.toString()
        binding.tvRaza.text = PetItem.raza.toString()

        itemView.setOnClickListener {
            // layouPosition devuelve la posicion en la lista del item
            OnItemSelected(layoutPosition)
        }

        itemView.setOnLongClickListener {
            OnLongItemSelected(layoutPosition)
            true
        }





    }
}