package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsPerfilModelo
import com.example.bibliodesk_movil.Vista.PerfilActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsPerfilPresentador(private val view: PerfilActivity) {
    private val modelo = clsPerfilModelo()

    fun obtenerPerfil(idUsuario: Int){
        if(idUsuario <= 0){
            view.mostrarError("No existe el usuario")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerPerfil(idUsuario)
                }
                if (respuesta.exito) {
                    view.mostrarPerfil(respuesta)
                } else {
                    view.mostrarError("No se encontraron datos de usuario")
                }

            }catch (e: Exception){
                view.mostrarError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}