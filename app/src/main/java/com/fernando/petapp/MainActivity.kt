package com.fernando.petapp

import android.app.Dialog
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernando.petapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

private lateinit var adapter: AdapterPet

val listaPets = mutableListOf(
    Pets(
        nombre = "Kiwi",
        raza = "American Stanford",
        imagenUrl = null
    ),
    Pets(
        nombre = "Kiwi",
        raza = "American Stanford",
        imagenUrl = R.drawable.kiwi_agua
    ),
    Pets(
        nombre = "Kiwi",
        raza = "American Stanford",
        imagenUrl = null
    ),
    Pets(
        nombre = "Kiwi",
        raza = "American Stanford",
        imagenUrl = null
    ),
    Pets(
        nombre = "Kiwi",
        raza = "American Stanford",
        imagenUrl = null
    )
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RecyclerInit()
        configListener()
    }

    private fun configListener() {

        binding.addDog.setOnClickListener {
            showDialog()
        }


    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_perros)
        dialog.show()

        val btSalir = dialog.findViewById<Button>(R.id.bt_salirDialog)
        val btIntroducirPerro = dialog.findViewById<Button>(R.id.bt_introducirPerro)
        val edNombrePerro = dialog.findViewById<EditText>(R.id.et_dialog_nombre)
        val edRazaPerro = dialog.findViewById<EditText>(R.id.et_dialog_raza)

        btSalir.setOnClickListener {
            cerrarDialogo(dialog)
        }

        btIntroducirPerro.setOnClickListener {
            if ( edNombrePerro.text.toString().isNotEmpty()  && edRazaPerro.text.toString().isNotEmpty()  ){
                listaPets.add(Pets(nombre = edNombrePerro.text.toString(), raza = edRazaPerro.text.toString(), imagenUrl = null))
                actualizarListadoRecycler()
                cerrarDialogo(dialog)
            } else {
                Log.i("Fernando", "$edNombrePerro $edRazaPerro")
                Log.i("Fernando", "Esta vacio")
                cerrarDialogo(dialog)
            }
        }

    }

    private fun actualizarListadoRecycler() {
        adapter.notifyDataSetChanged()
    }

    private fun cerrarDialogo(dialog: Dialog) {
        dialog.dismiss()
    }

    private fun RecyclerInit() {
        adapter = AdapterPet(listaPets)
        binding.rvRecyclerPet.setHasFixedSize(true)
        binding.rvRecyclerPet.layoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        binding.rvRecyclerPet.adapter = adapter
    }


}