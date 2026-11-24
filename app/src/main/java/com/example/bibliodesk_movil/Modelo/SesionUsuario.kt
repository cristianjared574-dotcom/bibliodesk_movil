package com.example.bibliodesk_movil.Modelo

object SesionUsuario {
    var idUsuario : Int=0
    var nombreCompleto : String=""
    var matricula : String =""
    var correo : String=""
    var telefono : String =""
    var tipoUsuario : String=""
    var estaActiva: Boolean = false

    fun cerrarSesion(){
        idUsuario =0
        nombreCompleto=""
        matricula=""
        correo=""
        telefono=""
        tipoUsuario=""
        estaActiva= false
    }

}