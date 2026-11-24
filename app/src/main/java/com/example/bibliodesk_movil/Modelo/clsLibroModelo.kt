// app/src/main/java/com/example/bibliodesk_movil/Modelo/clsLibroModelo.kt
package com.example.bibliodesk_movil.Modelo

data class clsLibroModelo(
    val id_libro: Int,
    val clave_libro: String,
    val id_editorial: Int,
    val id_categoria: Int,
    val isbn: String,
    val titulo: String,
    val descripcion: String,
    val anio_publicacion: Int,
    val edicion: String,
    val numero_paginas: Int,
    val ruta_portada: String,
    val estado: String,
    val nombre_editorial: String? = null,
    val nombre_categoria: String? = null,
    val nombre_autor: String? = null,
    val disponibilidad: String? = null
)