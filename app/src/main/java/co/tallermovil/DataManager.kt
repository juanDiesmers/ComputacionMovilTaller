package co.tallermovil

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

class DataManager(private val context: Context) {

    fun cargarDestinos(): List<Lugar> {
        val gson = Gson()
        val destinosJson = cargarJSONDesdeArchivo()
        val tipoLista = object : TypeToken<List<Lugar>>() {}.type
        return gson.fromJson(destinosJson, tipoLista)
    }

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
}
