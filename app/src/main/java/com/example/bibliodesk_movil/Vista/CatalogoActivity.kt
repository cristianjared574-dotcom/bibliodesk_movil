package com.example.bibliodesk_movil.Vista

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliodesk_movil.Adaptador.catalogoLibrosAdaptador
import com.example.bibliodesk_movil.Modelo.dataCatalogoLibros
import com.example.bibliodesk_movil.Modelo.dataCategorias
import com.example.bibliodesk_movil.Presentador.clsCategoriasPresentador
import com.example.bibliodesk_movil.R

class CatalogoActivity : AppCompatActivity() {
    private lateinit var presenter: clsCategoriasPresentador
    private lateinit var spinnerCategoria: Spinner
    private lateinit var recycleCatalogo: RecyclerView
    private lateinit var adapterCatalogoLibro: catalogoLibrosAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        spinnerCategoria =findViewById(R.id.spnCategorias)
        recycleCatalogo =findViewById(R.id.rvLibros)

        recycleCatalogo.layoutManager = LinearLayoutManager(this)
        adapterCatalogoLibro = catalogoLibrosAdaptador(this, emptyList())
        recycleCatalogo.adapter = adapterCatalogoLibro
        presenter=clsCategoriasPresentador(this)
        configurarListeners()
        configurarMenu()
        presenter.iniciar()
    }

    private fun configurarListeners(){
        spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position:Int,id:Long){
                presenter.seleccionarCategoria(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun configurarMenu() {

        val menuInferior = findViewById<BottomNavigationView>(R.id.menuInferior)

        // marcar catalogo como seleccionado
        menuInferior.selectedItemId = R.id.nav_catalogo

        // evento del menu inferior
        menuInferior.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    // obtener el ID del lector
                    val idUsuario = intent.getIntExtra("id_usuario", -1)

                    // abrir la pantalla de Inicio
                    val intentInicio = Intent(this, MainActivity::class.java)
                    intentInicio.putExtra("id_usuario", idUsuario)
                    intentInicio.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    startActivity(intentInicio)
                    finish()
                    true
                }
                R.id.nav_catalogo -> {
                    true
                }
                R.id.nav_prestamos -> {
                    false
                }
                R.id.nav_perfil -> {
                    false
                }
                else -> false
            }
        }
    }

    fun mostrarCategorias(categoria: List<dataCategorias>){
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoria)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapter
    }

    fun mostrarCatalogo(catalogo: List<dataCatalogoLibros>){
        adapterCatalogoLibro.actualizarLista(catalogo)
    }

    fun mostrarError(mensaje: String){
        android.util.Log.e("BiblioDeskAPI", mensaje)
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun abrirDetalle(catalogo: dataCatalogoLibros){

    }
}