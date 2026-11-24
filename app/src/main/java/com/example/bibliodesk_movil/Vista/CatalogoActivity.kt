package com.example.bibliodesk_movil.Vista

import android.annotation.SuppressLint
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
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import android.content.Intent
import android.speech.tts.TextToSpeech
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.util.TypedValue
import android.widget.TextView
import java.util.Locale
import android.view.MotionEvent

class CatalogoActivity : AppCompatActivity() {
    private lateinit var presenter: clsCategoriasPresentador
    private lateinit var spinnerCategoria: Spinner
    private lateinit var recycleCatalogo: RecyclerView
    private lateinit var adapterCatalogoLibro: catalogoLibrosAdaptador
    private lateinit var txtBuscarLibro: EditText

    private var librosDeCategoria: List<dataCatalogoLibros> = emptyList()

    //viriables para accesibilidad
    private var nivelTexto = 0
    private var lectorVoz: TextToSpeech? = null
    private var vozActivada = false

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
        txtBuscarLibro = findViewById(R.id.txtBuscarLibro)

        recycleCatalogo.layoutManager = LinearLayoutManager(this)
        adapterCatalogoLibro = catalogoLibrosAdaptador(this, emptyList())
        recycleCatalogo.adapter = adapterCatalogoLibro
        presenter=clsCategoriasPresentador(this)
        configurarListeners()
        configurarMenu()
        presenter.iniciar()

        txtBuscarLibro.doAfterTextChanged {
            filtrarLibros()
        }

        configurarAccesibilidad()
    }

    private fun configurarListeners(){
        spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position:Int,id:Long){
                presenter.seleccionarCategoria(position)

                //Accesibilidad
                val categoria = parent?.getItemAtPosition(position) as? dataCategorias
                if(categoria != null){
                    leerCategoria(categoria)
                }
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
        val adapter = ArrayAdapter(this,R.layout.item_spinner, categoria)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        findViewById<Spinner>(R.id.spnCategorias).adapter = adapter
    }

    fun mostrarCatalogo(catalogo: List<dataCatalogoLibros>){
        adapterCatalogoLibro.actualizarLista(catalogo)

        librosDeCategoria = catalogo
        filtrarLibros()
    }

    fun mostrarError(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

        if(vozActivada){
            lectorVoz?.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "error_login")
        }
    }
    // aqui se determina la disponiblidad
    fun abrirDetalle(catalogo: dataCatalogoLibros){
        val intent= android.content.Intent(this, DetalleLibroActivity::class.java).apply {
            putExtra("titulo",catalogo.titulo)
            putExtra("autor",catalogo.autor)
            putExtra("categoria", catalogo.categoria ?: "")
            putExtra("isbn",catalogo.isbn ?: "")
            putExtra("anio",catalogo.anioPublicacion?.toString()?:"")
            putExtra("paginas",catalogo.numeroPaginas?.toString()?:"")
            putExtra("ruta_portada",catalogo.rutaPortada)

            val disponibilidad = if (catalogo.ejemplaresDisponibles>0){
                "DISPONIBLE"
            }
            else{
                "NO DISPONIBLE"
            }
            putExtra("disponibilidad",disponibilidad)
        }
        startActivity(intent)
    }

    private fun filtrarLibros(){
        val texto = txtBuscarLibro.text.toString().trim()

        if(texto.isEmpty()){
            adapterCatalogoLibro.actualizarLista(librosDeCategoria)
            return
        }

        val textoAutor = texto.replace(".","").replace(" ","")

        val resultados = librosDeCategoria.filter{ libro ->

            val autor = libro.autor.orEmpty().replace(".","").replace(" ","")
            libro.titulo.contains(texto, ignoreCase = true) || (textoAutor.isNotEmpty() && autor.contains(textoAutor, ignoreCase = true))
        }

        adapterCatalogoLibro.actualizarLista(resultados)
    }

    private fun configurarMenu(){
        val menu = findViewById<BottomNavigationView>(R.id.menuInferior)

        //cuando abre esta pantalla, se marca catalogo
        menu.selectedItemId = R.id.nav_catalogo

        menu.setOnItemSelectedListener { opcion ->
            when(opcion.itemId){
                R.id.nav_catalogo -> true

                R.id.nav_inicio->{
                    startActivity(Intent(this, MainActivity::class.java))
                    false
                }

                R.id.nav_prestamos -> {
                    //startActivity(this, PrestamoActivity::class.java))
                    false
                }

                R.id.nav_perfil ->{
                    //startActivity(this, PrestamoActivity::class.java))
                    false
                }

                else -> false
            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configurarAccesibilidad(){
        val btnAumentar = findViewById<View>(R.id.btnAumentarTexto)
        val btnDisminuir = findViewById<View>(R.id.btnDisminuirTexto)
        val btnVoz = findViewById<View>(R.id.btnLecturaVoz)

        val tamaniosOriginales =txtBuscarLibro.textSize

        fun aplicarTamanio(){
            val factor = 1f + nivelTexto * 0.10f

            txtBuscarLibro.setTextSize(TypedValue.COMPLEX_UNIT_PX, tamaniosOriginales * factor)

            adapterCatalogoLibro.cambiarTamanioTexto(factor)

            btnAumentar.isEnabled = nivelTexto < 5
            btnDisminuir.isEnabled = nivelTexto > 0
        }



        btnAumentar.setOnClickListener{
            nivelTexto++
            aplicarTamanio()
        }

        btnDisminuir.setOnClickListener{
            nivelTexto--
            aplicarTamanio()
        }

        aplicarTamanio()

        btnVoz.isEnabled = false
        btnVoz.contentDescription = "Activar lectura en voz alta"

        lectorVoz = TextToSpeech(this){estado ->
            if(estado == TextToSpeech.SUCCESS){
                val idioma = lectorVoz?.setLanguage(Locale("es", "MX"))
                btnVoz.isEnabled = idioma != null && idioma >= 0
            }
        }

        btnVoz.setOnClickListener{
            vozActivada = !vozActivada
            btnVoz.isSelected = vozActivada

            if(vozActivada){
                btnVoz.contentDescription = "Desactivar lectura en voz alta"
                lectorVoz?.speak("Catálogo de Libros. Puedes buscar un libro por título o autor," +
                        " o seleccionar una Categoría",
                    TextToSpeech.QUEUE_FLUSH, null, "ayuda_catalogo")
            } else {
                btnVoz.contentDescription = "Activar lectura en voz alta"
                lectorVoz?.stop()
            }
        }
        txtBuscarLibro.setOnTouchListener { _, evento ->
            if(evento.action == MotionEvent.ACTION_DOWN && vozActivada){
                lectorVoz?.speak("Escribe el título del libro o el nombre del autor.",
                    TextToSpeech.QUEUE_FLUSH, null,"ayuda_busqueda"
                )
            }
            false
        }

    }

    fun leerLibro(libro: dataCatalogoLibros) {
        if (!vozActivada) return

        val autor = if (libro.autor.isNullOrBlank()) {
            "Autor sin registrar"
        } else {
            "Autor: ${libro.autor}"
        }

        val disponibilidad = if (libro.ejemplaresDisponibles > 0) {
            "Disponible"
        } else {
            "No disponible"
        }

        val mensaje = "${libro.titulo}. $autor. $disponibilidad. " +
                "Para conocer más, pulsa el botón Ver detalle."

        lectorVoz?.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "lectura_libro")
    }

    fun leerCategoria(categoria: dataCategorias){
        if(vozActivada){
            lectorVoz?.speak("Categoría: $categoria",
                TextToSpeech.QUEUE_FLUSH, null, "ayuda_categoria")
        }
    }


    //detener la voz al salir de la pantalla
    override fun onStop(){
        lectorVoz?.stop()
        super.onStop()
    }

    override fun onDestroy(){
        lectorVoz?.shutdown()
        super.onDestroy()
    }
}