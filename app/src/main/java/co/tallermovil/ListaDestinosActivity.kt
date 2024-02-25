package co.tallermovil

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

        // Obtener la lista de destinos del DataManager
        val destinos = dataManager.cargarDestinos()

        // Verificar si la lista de destinos no es nula
        if (destinos.isNotEmpty()) {
            // Obtener una referencia al ListView en el layout
            val listView: ListView = findViewById(R.id.listView)

            // Crear un adaptador para la lista de destinos
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, destinos.map { it.nombre })

            // Asignar el adaptador al ListView
            listView.adapter = adapter
        } else {
            // Si la lista de destinos es nula o vacía, mostrar un mensaje de error
            Toast.makeText(this, "No se encontraron destinos", Toast.LENGTH_SHORT).show()
        }
    }
}