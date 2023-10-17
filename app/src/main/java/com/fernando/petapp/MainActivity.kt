package com.fernando.petapp

import android.app.Dialog
import android.content.ContentValues.TAG
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var binding: ActivityMainBinding

private lateinit var adapter: AdapterPet

private lateinit var database: DatabaseReference



var listaPets = mutableListOf(
    Pet(
        id = 1,
        nombre = "Kiwi",
        raza = "American Stanford",
        imagenUrl = null
    ),
    Pet(
        id = 1,
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
        bdInit()
        FireBaseIniciador()

    }

    fun nuevoPerroFireBase(idPerroDB: String, id: Int, nombre: String, raza: String, url: String) {
        val perro = Pet(id, nombre, raza, url)

        database.child("Perros").child(idPerroDB).setValue(perro).addOnSuccessListener {
            Log.i("FireBase", "Se han subido los datos con exito")
        }
            .addOnFailureListener {
                Log.i("FireBase", "No se han subido los datos con exito")
            }

//        database.child("Perro").removeValue(null) para borrar sirve
        database.push()
    }

    private fun FireBaseIniciador() {
        database = Firebase.database.reference
        nuevoPerroFireBase("Perro2",4, "Kiwi5", "rarar", "rarrara")



        // FireBase trabaja con clave - Valor,  message es la clave y el valor se lo añadimos con "setValue"
//        val myRef = database.getReference("message")
//        myRef.setValue("Hello, World!")
//
//        // Read from the database, añadimos un listener a la variable o referencia directa database.getReference("message").addValueEventListener
//        database.getReference("valor1").addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = snapshot.getValue<Int>()
//                Log.d(TAG, "Value is: " + value)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })

    }

    private fun bdInit() {

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDataBase"
        ).build()

        val listaDePerros : Pet = Pet( null,"Sergio", "Comunista", null)

        CoroutineScope(Dispatchers.IO).launch {
            db.userDao().insertarPerro(listaDePerros)

            val listaDePerrosDevuelta = db.userDao().getAll()
//            Log.i("Fernando", "Esta es la lista devuelta: $listaDePerrosDevuelta")
            listaPets.plusAssign(listaDePerrosDevuelta)
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }



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
                listaPets.add(Pet( nombre = edNombrePerro.text.toString(), raza = edRazaPerro.text.toString(), imagenUrl = null))
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
        adapter = AdapterPet(listaPets, { position -> OnItemSelected(position) }) {position -> OnLongItemSelected(position)}
        binding.rvRecyclerPet.setHasFixedSize(true)
        binding.rvRecyclerPet.layoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        binding.rvRecyclerPet.adapter = adapter
    }

    private fun OnItemSelected(position: Int){
        Log.i("fernando", "Han tocado una ventana con un click en la pos $position")
    }

    private fun OnLongItemSelected(position: Int){
        Log.i("fernando", "Han tocado una ventana con un click largo en la pos $position")
    }


}