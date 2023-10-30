//package com.fernando.petapp
//
//import android.app.Dialog
//import android.content.ContentValues.TAG
//import android.icu.lang.UCharacter.VerticalOrientation
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.room.Room
//import com.fernando.petapp.databinding.ActivityMainBinding
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.database.ktx.getValue
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//private lateinit var binding: ActivityMainBinding
//
//private lateinit var adapter: AdapterPet
//
//private var contador: String = "0"
//
//// Inicializamos una lista vacia
//var listaPets = mutableListOf<Pet>(
//    Pet(id = 1, nombre = "bulldog", "bull", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Racib%C3%B3rz_2007_082.jpg/320px-Racib%C3%B3rz_2007_082.jpg")
//)
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        RecyclerInit()
//        configListener()
//        // Funcion de Room comentada
//        //        bdInit()
//        // Pruebas para fireStore
//        // FireBaseIniciador()
//
//    }
//
//    private fun removeDog(idDog: Int){
//        val db = Firebase.firestore
//
//        db.collection("perros").document(idDog.toString()).delete()
//    }
//
//    private fun readAllDogs(){
//
//        val db = Firebase.firestore
//
//        db.collection("perros")
//            .get()
//            .addOnSuccessListener { result -> // Saca una lista de objetos
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data.get("id")}")
//                    var idGet: Int = document.data.get("id").toString().toInt()
//                    var nombreGet: String = document.data.get("nombre").toString()
//                    var razaGet: String = document.data.get("raza").toString()
//                    var imagenUrlGet: String = document.data.get("imagenUrl").toString()
//
//                    listaPets.add(Pet(id = idGet, nombre = nombreGet, raza = razaGet, imagenUrl = imagenUrlGet ))
//                    actualizarListadoRecycler()
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//    }
//
//    private fun addDog(id: Int,nombre: String, raza: String, imageUrl: String  ){
//
//        val db = Firebase.firestore
//
//        val perros = db.collection("perros")
//        // Mapeamos el objeto
//        val data1 = hashMapOf(
//            "id" to id,
//            "nombre" to nombre,
//            "raza" to raza,
//            "imagenUrl" to imageUrl,
//        )
//
//        Log.i("FernandoFinal", "Este id es: $id")
//        // Creamos el objeto en Firebase FireStore
//        db.collection("perros").document( id.toString() ).set(data1)
//
//    }
//
//    private fun bdInit() {
//
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "AppDataBase"
//        ).build()
//
//        val listaDePerros : Pet = Pet( null,"Sergio", "Comunista", null)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            db.userDao().insertarPerro(listaDePerros)
//
//            val listaDePerrosDevuelta = db.userDao().getAll()
////            Log.i("Fernando", "Esta es la lista devuelta: $listaDePerrosDevuelta")
//            listaPets.plusAssign(listaDePerrosDevuelta)
//            runOnUiThread {
//                adapter.notifyDataSetChanged()
//            }
//        }
//
//
//
//    }
//
//    private fun configListener() {
//
//        binding.addDog.setOnClickListener {
//            showDialog()
//        }
//
//
//    }
//
//    private fun showDialog() {
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.dialog_perros)
//        dialog.show()
//
//        val btSalir = dialog.findViewById<Button>(R.id.bt_salirDialog)
//        val btIntroducirPerro = dialog.findViewById<Button>(R.id.bt_introducirPerro)
//        val edNombrePerro = dialog.findViewById<EditText>(R.id.et_dialog_nombre)
//        val edRazaPerro = dialog.findViewById<EditText>(R.id.et_dialog_raza)
//        val edImagenUrl = dialog.findViewById<EditText>(R.id.et_dialog_imagenurl)
//
//        btSalir.setOnClickListener {
//            cerrarDialogo(dialog)
//        }
//
//        btIntroducirPerro.setOnClickListener {
//            if ( edNombrePerro.text.toString().isNotEmpty()  && edRazaPerro.text.toString().isNotEmpty() && edImagenUrl.text.toString().isNotEmpty() ){
//                val contActual : String = getContadorFireBase()
//                Log.i("Fernando", "El contador actual al añadirlo es $contActual")
//                addDog(contActual.toInt(), btIntroducirPerro.text.toString(), edRazaPerro.text.toString(), edImagenUrl.text.toString())
//                readAllDogs()
//                actualizarListadoRecycler()
//                cerrarDialogo(dialog)
//            } else {
//                Log.i("Fernando", "$edNombrePerro $edRazaPerro")
//                Log.i("Fernando", "Esta vacio")
//                cerrarDialogo(dialog)
//            }
//        }
//
//    }
//
//    private fun getContadorFireBase() : String{
//        val db = Firebase.firestore
//
//        // Leemos el Contador
//        db.collection("contador").get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    // Sacamos el contador del FireBase, esta no es la mejor forma por rendimiento y sincronizacion
//                     contador  = document.data.get("cont").toString()
//                    Log.i("Fernando", "Al leer el contador este vale $contador")
//                    // Añadimos uno
//                    addContador(contador)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error recogiendo el contador")
//            }
//
//        Log.i("Fernando", "Al leer el contador en el retorno este vale $contador")
//        return contador
//    }
//
//    private fun addContador(contador: String){
//        // Añadimos uno
//        val db = Firebase.firestore
//        var calculo = contador.toInt() + 1
//        var contadorAc : String = calculo.toString()
//        db.collection("contador").document("contadorGeneral")
//            .update("cont", contadorAc)
//            .addOnSuccessListener {
//                Log.i("Fernando", "Se ha actualizado con exito el contador")
//        } .addOnFailureListener {
//                Log.w("Fernando", "No se ha actualizado el contador")
//            }
//    }
//
//    private fun actualizarListadoRecycler() {
//        adapter.notifyDataSetChanged()
//    }
//
//    private fun cerrarDialogo(dialog: Dialog) {
//        dialog.dismiss()
//    }
//
//    private fun RecyclerInit() {
//        readAllDogs()
//        adapter = AdapterPet(listaPets, { petPosition -> OnItemSelected(petPosition) }) {petPosition -> OnLongItemSelected(petPosition)}
//        binding.rvRecyclerPet.setHasFixedSize(true)
//        binding.rvRecyclerPet.layoutManager =
//            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
//        binding.rvRecyclerPet.adapter = adapter
//    }
//
//    private fun OnItemSelected(petPosition: Int?){
//        Log.i("fernando", "Han tocado una ventana con un click en la pos $petPosition")
//    }
//
//    private fun OnLongItemSelected(petPosition: Int){
//        removeDog(petPosition)
//        readAllDogs()
//        Log.i("fernando", "Han tocado una ventana con un click largo en la pos $petPosition")
//    }
//
//
//}