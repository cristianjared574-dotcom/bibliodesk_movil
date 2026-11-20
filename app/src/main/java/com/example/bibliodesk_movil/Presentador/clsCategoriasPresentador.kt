package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk.modelo.clsCatalogoLibrosModelo
import com.example.bibliodesk_movil.Modelo.clsCategoriasModelo
import com.example.bibliodesk_movil.Modelo.dataCatalogoLibros
import com.example.bibliodesk_movil.Modelo.dataCategorias
import com.example.bibliodesk_movil.Vista.CatalogoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCategoriasPresentador(private val vista: CatalogoActivity) {
    private val modeloCatalogLibros=clsCatalogoLibrosModelo()
    private val modelo = clsCategoriasModelo()
    private var listaCategorias: List<dataCategorias> = emptyList()

    fun iniciar(){

        CoroutineScope(Dispatchers.Main).launch {
            try{
                listaCategorias = modelo.obtenerCategorias()
                vista.mostrarCategorias(listaCategorias)

                cargarCatalogo(0)
            }catch (e: Exception){
                vista.mostrarError("Error: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarCategoria(posicion: Int){
        if(posicion >= 0 && posicion < listaCategorias.size){
            val categoria = listaCategorias[posicion]
            cargarCatalogo(categoria.idCategoria)
        }
    }

    private fun cargarCatalogo(idcategoria: Int){
        CoroutineScope(Dispatchers.Main).launch {
            try{
                val catalogo = modeloCatalogLibros.obtenerLibros(idcategoria)
                vista.mostrarCatalogo(catalogo)
            }catch (e: Exception){
                vista.mostrarError("Error al cargar las categorias" + e.localizedMessage)
            }
        }
    }

    fun seleccionarCatalogo(catalogo: dataCatalogoLibros){
        vista.abrirDetalle(catalogo)
    }

}