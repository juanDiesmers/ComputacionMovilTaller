package co.tallermovil

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var dataManager: DataManager
    private lateinit var spinner: Spinner
    private val listaFavoritos = mutableListOf<Lugar>()
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataManager = DataManager(this)
        spinner = findViewById(R.id.spinner)
        //Cargar la lista de favoritos
        listaFavoritos.addAll(dataManager.cargarDestinosFavoritos())

        //Explorar Destinos
        val explorarButton: Button = findViewById(R.id.button)
        explorarButton.setOnClickListener {
            //Obtener el valor seleccionado del Spinner
            val categoriaSeleccionada = spinner.selectedItem.toString()
            //Crear un intent y añadir la categoria seleccionada
            val intent = Intent(this, ListaDestinosActivity::class.java)
            intent.putExtra("categoria", categoriaSeleccionada)
            //Iniciar la activiada ListaDestinoActivity
            startActivity(intent)
        }

        //Boton para ver destinos favoritos
        val favoritosButton: Button = findViewById(R.id.button2)
        favoritosButton.setOnClickListener {
            //Abrir la actividad de favoritos
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
        }

        //Boton para recomendados
        val btnRecomendaciones: Button = findViewById(R.id.button3)
        btnRecomendaciones.setOnClickListener {
            mostrarRecomendaciones()
        }

            // Obtener la matriz de cadenas desde categoria.xml
            val opciones = resources.getStringArray(R.array.Categorias)
            //Crear un Array para el spinner
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Asignar el ArrayAdaptor al spinner
            spinner.adapter = adapter
    }

    private fun mostrarRecomendaciones() {
        val listaFavoritos = dataManager.cargarDestinosFavoritos()
        //Verificar si hay favoritos
        if (listaFavoritos.isEmpty()) {
            // No hay favoritos, mostrar "NA"
            Toast.makeText(this, "NA", Toast.LENGTH_SHORT).show()
        } else {
            // Obtener la categoria mas frecuente
            val categoriaMasFrecuente = obtenerCategoriaMasFrecuente()
            // Filtrar los favoritos por la categoria mas frecuente
            val destinos = dataManager.cargarDestinosFavoritos()
            val favoritosDeCategoria = destinos.filter { it.categoria == categoriaMasFrecuente }

            if (favoritosDeCategoria.isNotEmpty()) {
                // Obtener un destino aleatorio de los favoritos de la categoria mas frecuente
                val destinoAleatorio = favoritosDeCategoria.random()
                // Lanzar la actividad para mostrar el nombre de la actividad recomendada
                val intent = Intent(this, DetallesDestinoActivity::class.java)

                intent.putExtra("destino", destinoAleatorio)

                startActivity(intent)
            } else {
                // No hay favoritos en la categoria mas frecuente
                Toast.makeText(this, "NA", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerCategoriaMasFrecuente(): String {
        // Obtener la lista de destinos favoritos
        val destinosFavoritos = dataManager.cargarDestinosFavoritos()

        // Mapa para almacenar la frecuencia de cada categoría
        val frecuenciaCategorias = mutableMapOf<String, Int>()

        // Calcular la frecuencia de cada categoría en la lista de favoritos
        for (favorito in destinosFavoritos) {
            val categoria = favorito.categoria ?: continue
            frecuenciaCategorias[categoria] = frecuenciaCategorias.getOrDefault(categoria, 0) + 1
        }

        // Encontrar la categoría con la frecuencia más alta
        var categoriaMasFrecuente = "NA"
        var maxFrecuencia = 0
        for ((categoria, frecuencia) in frecuenciaCategorias) {
            if (frecuencia > maxFrecuencia) {
                categoriaMasFrecuente = categoria
                maxFrecuencia = frecuencia
            }
        }

        return categoriaMasFrecuente
    }
}
