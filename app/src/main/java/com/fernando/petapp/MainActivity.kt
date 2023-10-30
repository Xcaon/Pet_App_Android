package com.fernando.petapp

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.fernando.petapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var binding: ActivityMainBinding

private lateinit var adapter: AdapterPet


// Inicializamos una lista vacia
var listaPets = mutableListOf<Pet>(
    Pet(id = 1, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),
    Pet(id = 2, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),
    Pet(id = 3, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),
    Pet(id = 4, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),
    Pet(id = 5, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),
    Pet(id = 6, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),
    Pet(id = 7, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg"),


)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RecyclerInit()
        menuInferiorListener()

    }

    private fun menuInferiorListener() {
        val menuAbajo = binding.menuBottomOpciones
        menuAbajo.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuItemInicio -> {
                    loadActivity("MainActivity")
                    true
                }
                R.id.menuItemFavoritos -> {
                    Log.i("Fernando", "hola2");
                    true
                }
                R.id.menuItemNovedades -> {
                    Log.i("Fernando", "hola3");
                    true
                }
                R.id.menuItemMensajes -> {
                    Log.i("Fernando", "hola2");
                    true
                }
                R.id.menuItemTu -> {
                    loadActivity("activityPerfil")
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadActivity(actividad: String){

        when(actividad) {
            "activityPerfil" -> {
                val intent = Intent(this, activityPerfil::class.java)
                startActivity(intent)
            }
            "MainActivity" -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }


//    private fun configListener() {
//
//        binding.addDog.setOnClickListener {
//            showDialog()
//        }
//
//
//
//    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_perros)
        dialog.show()


        val btSalir = dialog.findViewById<Button>(R.id.bt_salirDialog)
        val btIntroducirPerro = dialog.findViewById<Button>(R.id.bt_introducirPerro)
        val edNombrePerro = dialog.findViewById<EditText>(R.id.et_dialog_nombre)
        val edRazaPerro = dialog.findViewById<EditText>(R.id.et_dialog_raza)
        val edImagenUrl = dialog.findViewById<EditText>(R.id.et_dialog_imagenurl)

        btSalir.setOnClickListener {
            cerrarDialogo(dialog)
        }

        btIntroducirPerro.setOnClickListener {
            if ( edNombrePerro.text.toString().isNotEmpty()  && edRazaPerro.text.toString().isNotEmpty() && edImagenUrl.text.toString().isNotEmpty() ){
                listaPets.add(Pet(0, edNombrePerro.text.toString(), edRazaPerro.text.toString(), edImagenUrl.text.toString() ))
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
        adapter = AdapterPet(listaPets, { petPosition -> OnItemSelected(petPosition) }) {petPosition -> OnLongItemSelected(petPosition)}
        binding.rvRecyclerPet.setHasFixedSize(true)
        binding.rvRecyclerPet.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        binding.rvRecyclerPet.adapter = adapter
    }

    private fun OnItemSelected(petPosition: Int?){
        Log.i("fernando", "Han tocado una ventana con un click en la pos $petPosition")
    }

    private fun OnLongItemSelected(petPosition: Int){
        Log.i("fernando", "Han tocado una ventana con un click largo en la pos $petPosition")
    }


}