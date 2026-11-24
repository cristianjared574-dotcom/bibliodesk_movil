package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.SesionUsuario
import com.example.bibliodesk_movil.Modelo.clsLoginModelo
import com.example.bibliodesk_movil.Vista.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsLoginPresentador(private val view: LoginActivity) {
    private val modelo = clsLoginModelo()

    fun realizarLogin(usuario: String, clave: String){
        if(usuario.isEmpty() || clave.isEmpty()){
            view.mostrarError("Por favor, ingresa usuario y contraseña")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try{
                val respuesta = withContext(Dispatchers.IO){
                    modelo.iniciarSesion(usuario, clave)
                }
                if(respuesta.exito){
                    // Aqui se guardan ls datos de la sesion
                    SesionUsuario.idUsuario=respuesta.idUsuario
                    SesionUsuario.nombreCompleto=respuesta.nombre
                    SesionUsuario.matricula= respuesta.matricula
                    SesionUsuario.correo= respuesta.correo
                    SesionUsuario.telefono=respuesta.telefono
                    SesionUsuario.tipoUsuario=respuesta.tipoUsuario
                    SesionUsuario.estaActiva=true

                    view.loginExitoso(respuesta.mensaje,respuesta.nombre)
                }else{
                    view.mostrarError(respuesta.mensaje)
                }
            }catch (e: Exception){
                view.mostrarError("Error de conexión: ${e.localizedMessage?: "Error desconocido"}")
            }
        }
    }
}