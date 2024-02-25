package co.tallermovil

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ListaDestinosActivity : AppCompatActivity() {

    private val dataManager = DataManager(this) // Crear una instancia de DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_destinos)

        val listView: ListView = findViewById(R.id.listView)
        // Obtener la lista de destinos del DataManager
        val destinos = dataManager.cargarDestinos()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, destinos.map { it.nombre })
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val destinoSeleccionado = destinos[position]
            val intent = Intent(this, DetallesDestinoActivity::class.java).apply {
                putExtra("destino", destinoSeleccionado)
            }
            startActivity(intent)
        }
    }
}

