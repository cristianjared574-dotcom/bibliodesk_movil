package com.example.bibliodesk_movil.InterfaceApi

import com.example.bibliodesk_movil.Modelo.dataCatalogoLibros
import com.example.bibliodesk_movil.Modelo.dataCategorias
import com.example.bibliodesk_movil.Modelo.dataLoginRequest
import com.example.bibliodesk_movil.Modelo.dataLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("obtener_categorias.php")
    suspend fun obtenerCategorias(): List<dataCategorias>

    @GET("obtener_libros.php")
    suspend fun obtenerLibros(@Query("id_categoria") idCategoria: Int): List<dataCatalogoLibros>

    @POST("login.php")
    suspend fun iniciarSesion(@Body request: dataLoginRequest): dataLoginResponse

}