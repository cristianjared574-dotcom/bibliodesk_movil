package com.example.bibliodesk_movil.InterfaceApi

import com.example.bibliodesk_movil.Modelo.dataCatalogoLibros
import com.example.bibliodesk_movil.Modelo.dataCategorias
import com.example.bibliodesk_movil.Modelo.dataCerrarSesionResponse
import com.example.bibliodesk_movil.Modelo.dataInicioResponse
import com.example.bibliodesk_movil.Modelo.dataLibroDetalleResponse
import com.example.bibliodesk_movil.Modelo.dataLoginRequest
import com.example.bibliodesk_movil.Modelo.dataLoginResponse
import com.example.bibliodesk_movil.Modelo.dataPerfilResponse
import com.example.bibliodesk_movil.Modelo.dataRegistroRequest
import com.example.bibliodesk_movil.Modelo.dataRegistroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("obtener_categorias.php")
    suspend fun obtenerCategorias(): List<dataCategorias>

    @GET("obtener_libros.php")
    suspend fun obtenerLibros(@Query("id_categoria") idCategoria: Int): List<dataCatalogoLibros>

    @GET("libro_detalle.php")
    suspend fun obtenerDetalleLibro(@Query("id_libro")idLibro: Int
    ): dataLibroDetalleResponse

    @POST("login.php")
    suspend fun iniciarSesion(@Body request: dataLoginRequest): dataLoginResponse

    @GET("perfil_usuario.php")
    suspend fun obtenerPerfil(@Query("id_usuario")idUsuario: Int): dataPerfilResponse

    @GET("cerrar_sesion.php")
    suspend fun cerrarSesion(): dataCerrarSesionResponse

    @POST("registrar_usuario.php")
    suspend fun registrarUsuario(@Body request: dataRegistroRequest): Response<dataRegistroResponse>

    @GET("obtener_inicio.php")
    suspend fun obtenerInicio(@Query("id_usuario") idUsuario: Int): dataInicioResponse

}