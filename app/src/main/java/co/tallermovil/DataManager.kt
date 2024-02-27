package co.tallermovil

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

interface FavoritoObserver {
    fun onFavoritosChanged()
}
class DataManager(private val context: Context) {
    //Metodo para inicializar el arcg¿hivo destinos_favoritos.json si no exist
    fun initFile() {
        val file = File(context.filesDir, "destinos_favoritos.json")
        if (!file.exists()) {
            file.createNewFile()
        }
    }
    //Metodo para cargar la lista de destinos desde el archivo JSON
    fun cargarDestinos(): List<Lugar> {
        val gson = Gson()
        val destinosJson = cargarJSONDesdeArchivo()
        val tipoLista = object : TypeToken<List<Lugar>>() {}.type
        return gson.fromJson(destinosJson, tipoLista)
    }
    // Método para guardar un destino como favorito
    fun guardarDestinoComoFavorito(destino: Lugar) {
        // Obtener la lista actual de destinos favoritos
        val destinosFavoritos = cargarDestinosFavoritos()

        // Agregar el nuevo destino a la lista de favoritos si no está presente
        if (!destinosFavoritos.contains(destino)) {
            destinosFavoritos.add(destino)
            // Guardar la lista actualizada de destinos favoritos en el archivo JSON
            guardarDestinosFavoritos(destinosFavoritos)
        }
    }
    // Método para cargar la lista de destinos favoritos desde el archivo JSON
    fun cargarDestinosFavoritos(): MutableList<Lugar> {
        val gson = Gson()
        val destinosFavoritosJson = context.openFileInput("destinos_favoritos.json")?.use {
            BufferedReader(InputStreamReader(it)).readText()
        } ?: ""
        try {
            val tipoLista = object : TypeToken<MutableList<Lugar>>() {}.type
            return gson.fromJson(destinosFavoritosJson, tipoLista) ?: mutableListOf()
        } catch (e: JsonSyntaxException) {
            // Manejar la excepción aquí
            e.printStackTrace()
            return mutableListOf()
        }
    }
    // Método para guardar la lista de destinos favoritos en el archivo JSON
    fun guardarDestinosFavoritos(destinosFavoritos: List<Lugar>) {
        val gson = Gson()
        val destinosFavoritosJson = gson.toJson(destinosFavoritos)
        context.openFileOutput("destinos_favoritos.json", Context.MODE_PRIVATE).use {
            OutputStreamWriter(it).use { writer ->
                writer.write(destinosFavoritosJson)
            }
        }
    }
    // Método para cargar un destino específico desde el archivo JSON (no utilizado en este ejemplo)
    fun obtenerDetallesDestino(id: Int): Lugar? {
        val destinos = cargarDestinos()
        return destinos.find { it.id == id } // Busca el destino con el ID proporcionado y devuelve ese destino
    }
    // Metodo para cargar el archivo JSON desde el directorio raw
    private fun cargarJSONDesdeArchivo(): String {
        val inputStream = context.resources.openRawResource(R.raw.destinos)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }

    fun cargarDestinosPorCategoria(categoria: String): List<Lugar> {
        val destinos = cargarDestinos()
        return destinos.filter { it.categoria == categoria }
    }
}