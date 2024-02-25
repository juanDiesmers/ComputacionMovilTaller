package co.tallermovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private lateinit var dataManager: DataManager
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataManager = DataManager(this)
        spinner = findViewById(R.id.spinner)

        //Explorar Destinos
        val explorarButton: Button = findViewById(R.id.button)
        explorarButton.setOnClickListener {
            val intent = Intent(this, ListaDestinosActivity::class.java)
            startActivity(intent)
        }

        try {
            //Cargar los destinos
            val destinos = dataManager.cargarDestinos()

            //Obtener nombres de destinos
            val nombreDestino = destinos.map { it.nombre }

            //Crear un Array para el spinner
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombreDestino)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            //Asignar el ArrayAdaptor al spinner
            spinner.adapter = adapter
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al cargar destinos: ${e.message}", e)
        }
    }
}