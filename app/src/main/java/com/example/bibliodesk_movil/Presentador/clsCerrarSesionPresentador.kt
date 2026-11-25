package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsCerrarSesionModelo
import com.example.bibliodesk_movil.Vista.CerrarSesionActivity
import com.example.bibliodesk_movil.Vista.PerfilActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsCerrarSesionPresentador(private val view: CerrarSesionActivity) {
    private val modelo = clsCerrarSesionModelo()
    fun cerrarSesion(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta= withContext(Dispatchers.IO){
                    modelo.cerrarSesion()
                }
                if (respuesta.exito){
                    view.cerrarSesionExitoso(respuesta.mensaje)
                }
                else{
                    view.mostrarError(respuesta.mensaje)
                }
            }catch (e: Exception){
                view.mostrarError( "Error al cerrar sesión:${e.localizedMessage?:"Error desconocida"}")
            }
        }
    }

}