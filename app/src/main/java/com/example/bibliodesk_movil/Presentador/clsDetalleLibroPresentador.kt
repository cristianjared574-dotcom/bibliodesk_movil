package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsDetalleLibroModelo
import com.example.bibliodesk_movil.Vista.DetalleLibroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleLibroPresentador(private val view: DetalleLibroActivity) {
    private val modelo = clsDetalleLibroModelo()

    fun cargarDetalle(idLibro: Int){
        CoroutineScope(Dispatchers.Main).launch {
            try{
                val libro = modelo.obtenerDetalleLibro(idLibro)

                if(libro.exito && libro.datos != null){
                    view.mostrarDetalleLibro(libro.datos)
                }else{
                    view.mostrarError(libro.mensaje)
                }
            }catch (e: Exception){
                view.mostrarError("Error:  ${e.localizedMessage ?:"Nose pudo conectar cn el servidor"}")
            }
        }
    }
}