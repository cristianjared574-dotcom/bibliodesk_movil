package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsRegistroModelo {
    suspend fun registrarUsuario(
        matricula: String, nombre: String, apellidoPaterno: String, apellidoMaterno: String,
        correo: String, telefono: String, contrasena: String, confirmarContrasena: String): dataRegistroResponse {

        val request = dataRegistroRequest(matricula = matricula, nombre = nombre, apellidoPaterno = apellidoPaterno,
            apellidoMaterno = apellidoMaterno, correo = correo, telefono = telefono, contrasena = contrasena,
            confirmarContrasena = confirmarContrasena
        )
        return RetrofitCliente.apiService.registrarUsuario(request)
    }
}