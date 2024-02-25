package co.tallermovil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavoritosActivity : AppCompatActivity() {

    private lateinit var dataManager: DataManager
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        dataManager = DataManager(this)
        listView = findViewById(R.id.listViewFavoritos)

        // Obtener la lista de destinos favoritos
        val destinosFavoritos = dataManager.cargarDestinosFavoritos()

        // Crear un adaptador personalizado y asignarlo al ListView
        val adapter = FavoritosAdapter(this, destinosFavoritos)
        listView.adapter = adapter
    }
}

class FavoritosAdapter(context: Context, private val destinos: List<Lugar>) : ArrayAdapter<Lugar>(context, 0, destinos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.item_destino, parent, false)
        }

        val destino = destinos[position]

        // Obtener referencias a las vistas en item_destino.xml
        val nombreTextView = listItemView!!.findViewById<TextView>(R.id.nombreTextView)
        val paisTextView = listItemView.findViewById<TextView>(R.id.paisTextView)
        val categoriaTextView = listItemView.findViewById<TextView>(R.id.categoriaTextView)
        val planTextView = listItemView.findViewById<TextView>(R.id.planTextView)
        val precioTextView = listItemView.findViewById<TextView>(R.id.precioTextView)

        // Establecer los datos del destino en las vistas
        nombreTextView.text = destino.nombre
        paisTextView.text = "País: ${destino.pais}"
        categoriaTextView.text = "Categoría: ${destino.categoria}"
        planTextView.text = "Plan: ${destino.plan}"
        precioTextView.text = "Precio: ${destino.precio}"

        return listItemView
    }
}


