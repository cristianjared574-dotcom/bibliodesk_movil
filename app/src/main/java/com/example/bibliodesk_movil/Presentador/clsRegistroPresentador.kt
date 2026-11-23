package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsRegistroModelo
import com.example.bibliodesk_movil.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsRegistroPresentador (private val view: RegistroActivity) {

    private val modelo = clsRegistroModelo()

    fun registrarUsuario(matricula: String, nombre: String, apellidoPaterno: String, apellidoMaterno: String,
        correo: String, telefono: String, contrasena: String, confirmarContrasena: String) {

        // validar campos obligatorios
        if (matricula.isBlank() || nombre.isBlank() || apellidoPaterno.isBlank() || correo.isBlank() ||
            telefono.isBlank() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {

            view.mostrarError("Por favor, completa todos los campos obligatorios")
            return
        }

        // validar que las contraseñas coincidan
        if (contrasena != confirmarContrasena) {
            view.mostrarError("Las contraseñas no coinciden")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                //trim quita espacios
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.registrarUsuario(matricula.trim(), nombre.trim(), apellidoPaterno.trim(),
                        apellidoMaterno.trim(), correo.trim(), telefono.trim(), contrasena, confirmarContrasena
                    )
                }

                if (respuesta.exito) {
                    view.registroExitoso(respuesta.mensaje)
                } else {
                    view.mostrarError(respuesta.mensaje)
                }

            } catch (e: Exception) {
                view.mostrarError(
                    "Error al registrar: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }
}