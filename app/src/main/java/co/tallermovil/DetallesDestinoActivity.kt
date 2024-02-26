package co.tallermovil

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetallesDestinoActivity : AppCompatActivity() {

    private val dataManager = DataManager(this) // Instancia de DataManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_destino)
        // Inicializa el archivo destinos_favoritos.json
        dataManager.initFile()
        // Obtener el destino seleccionado del Intent
        val destino: Lugar = intent.getParcelableExtra("destino") ?: return
        // Obtener los detalles del destino del DataManager utilizando su identificador o cualquier otro criterio
        val detallesDestino = dataManager.obtenerDetallesDestino(destino.id) ?: return
        // Actualizar la interfaz de usuario con los detalles del destino
        actualizarInterfazUsuario(detallesDestino)
        //Configurar el evento de clic para el boton "Agregar a favoritos"
        val btnAgregarFavoritos: Button = findViewById(R.id.btnAgregarFavoritos)
        btnAgregarFavoritos.setOnClickListener {
            //Guardar el destino como favorito
            guardarDestinoComoFavorito(detallesDestino)
            //Mostrar unu mensaje indicando que se ha agregado a favoritos
            Toast.makeText(this,"Destino agregado a favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarInterfazUsuario(detallesDestino: Lugar) {
        findViewById<TextView>(R.id.nombreTextView).text = "Nombre: ${detallesDestino.nombre}"
        findViewById<TextView>(R.id.paisTextView).text = "País: ${detallesDestino.pais}"
        findViewById<TextView>(R.id.categoriaTextView).text = "Categoría: ${detallesDestino.categoria}"
        findViewById<TextView>(R.id.planTextView).text = "Plan: ${detallesDestino.plan}"
        findViewById<TextView>(R.id.precioTextView).text = "Precio: ${detallesDestino.precio} USD"
    }

    private fun guardarDestinoComoFavorito(destino: Lugar) {
        dataManager.guardarDestinoComoFavorito(destino)
    }
}