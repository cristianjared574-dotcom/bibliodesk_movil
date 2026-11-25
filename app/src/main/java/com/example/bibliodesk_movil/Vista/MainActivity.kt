package com.example.bibliodesk_movil.Vista

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bibliodesk_movil.Modelo.dataInicioResponse
import com.example.bibliodesk_movil.Presentador.clsInicioPresentador
import com.example.bibliodesk_movil.R
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Button
import android.util.TypedValue
import android.speech.tts.TextToSpeech
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: clsInicioPresentador

    private lateinit var txtSaludo: TextView
    private lateinit var txtTituloLibro: TextView
    private lateinit var txtAutorLibro: TextView
    private lateinit var btnVerCatalogo: Button
    private lateinit var txtFechaLimite: TextView
    private lateinit var txtEstadoPrestamo: TextView
    private lateinit var imgPortadaPrestamo: ImageView
    private lateinit var cardPrestamoActual: View
    private lateinit var txtSinPrestamos: TextView
    private lateinit var menuInferior: BottomNavigationView
    private var nivelTexto = 0
    private var lectorVoz: TextToSpeech? = null
    private var vozActivada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        txtSaludo = findViewById(R.id.txtSaludo)
        txtTituloLibro = findViewById(R.id.txtTituloLibro)
        txtAutorLibro = findViewById(R.id.txtAutorLibro)
        btnVerCatalogo = findViewById(R.id.btnVerCatalogo)
        txtFechaLimite = findViewById(R.id.txtFechaLimite)
        txtEstadoPrestamo = findViewById(R.id.txtEstadoPrestamo)
        imgPortadaPrestamo = findViewById(R.id.imgPortadaPrestamo)
        cardPrestamoActual = findViewById(R.id.cardPrestamoActual)
        txtSinPrestamos = findViewById(R.id.txtSinPrestamos)
        menuInferior = findViewById(R.id.menuInferior)
        configurarTamanioTexto()
        configurarLectura()
        configurarMenu()

        presenter = clsInicioPresentador(this)

        btnVerCatalogo.setOnClickListener(this::abrirCatalogo)


        // evento del menu inferior
        menuInferior.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_inicio -> {
                    true
                }

                R.id.nav_catalogo -> {
                    val intent = Intent(this, CatalogoActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


        // obtener el id del usuario enviado desde Login
        val idUsuario = intent.getIntExtra("id_usuario", -1)

        if (idUsuario > 0) {
            presenter.obtenerInicio(idUsuario)
        } else {
            txtSaludo.text = "¡Bienvenido a BiblioDesk!"
            cardPrestamoActual.visibility = View.GONE
            txtSinPrestamos.visibility = View.GONE
        }


        // Seleccionar Inicio en el menú inferior
        menuInferior.selectedItemId = R.id.nav_inicio

        // Evento del menú inferior
        menuInferior.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_inicio -> {
                    true
                }

                R.id.nav_catalogo -> {
                    val intent = Intent(this, CatalogoActivity::class.java)
                    intent.putExtra("id_usuario", idUsuario)
                    startActivity(intent)
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

    fun abrirCatalogo(v: View) {
        val idUsuario = intent.getIntExtra("id_usuario", -1)

        val intent = Intent(this, CatalogoActivity::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)
    }


    private fun configurarTamanioTexto() {

        // obtener botones de accesibilidad
        val btnAumentar = findViewById<View>(R.id.btnAumentarTexto)
        val btnDisminuir = findViewById<View>(R.id.btnDisminuirTexto)

        // textos que cambiaran de tamaño
        val textos = listOf<TextView>(findViewById(R.id.txtNombreSistema), txtSaludo, findViewById(R.id.txtBienvenida),
            findViewById(R.id.txtTituloPrestamo), txtTituloLibro, txtAutorLibro, txtFechaLimite, txtEstadoPrestamo,
            txtSinPrestamos, findViewById(R.id.btnVerCatalogo), findViewById(R.id.btnVerPrestamo), findViewById(R.id.btnPaginaOficial)
        )

        // guardar los tamaños originales
        val tamaniosOriginales = textos.map { it.textSize }

        // aplicar el tamaño seleccionado
        fun aplicarTamanio() {
            val factor = 1f + nivelTexto * 0.10f

            textos.forEachIndexed { indice, texto ->
                texto.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    tamaniosOriginales[indice] * factor
                )
            }

            btnAumentar.isEnabled = nivelTexto < 5
            btnDisminuir.isEnabled = nivelTexto > 0
        }

        // A+
        btnAumentar.setOnClickListener {
            nivelTexto++
            aplicarTamanio()
        }

        // A-
        btnDisminuir.setOnClickListener {
            nivelTexto--
            aplicarTamanio()
        }
        aplicarTamanio()
    }


    private fun configurarLectura() {

        // Obtener el botón de accesibilidad
        val btnLectura = findViewById<View>(R.id.btnAccesibilidad)

        // Preparar el lector de voz
        btnLectura.isEnabled = false

        lectorVoz = TextToSpeech(this) { estado ->
            if (estado == TextToSpeech.SUCCESS) {

                val idioma = lectorVoz?.setLanguage(
                    Locale.forLanguageTag("es-MX")
                )

                btnLectura.isEnabled =
                    idioma != null &&
                            idioma != TextToSpeech.LANG_MISSING_DATA &&
                            idioma != TextToSpeech.LANG_NOT_SUPPORTED
            }
        }

        // Evento del botón
        btnLectura.setOnClickListener {

            if (vozActivada) {

                // Detener la lectura
                lectorVoz?.stop()
                vozActivada = false

            } else {

                // obtener los textos de Inicio
                val saludo = txtSaludo.text.toString()

                val bienvenida = findViewById<TextView>(R.id.txtBienvenida).text.toString()

                // preparar el contenido para leer
                val contenido = if (cardPrestamoActual.visibility == View.VISIBLE && txtTituloLibro.text.isNotBlank()) {

                    "$saludo. $bienvenida. " +
                            "Préstamo actual: ${txtTituloLibro.text}. " +
                            "Autor: ${txtAutorLibro.text}. " +
                            "${txtFechaLimite.text}. " +
                            "Estado: ${txtEstadoPrestamo.text}."

                } else if (txtSinPrestamos.visibility == View.VISIBLE) {

                    "$saludo. $bienvenida. ${txtSinPrestamos.text}"

                } else {

                    "$saludo. $bienvenida"
                }
                // leer el contenido
                lectorVoz?.speak(
                    contenido,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "lectura_inicio"
                )
                vozActivada = true
            }
        }
    }

    override fun onDestroy() {
        lectorVoz?.stop()
        lectorVoz?.shutdown()
        super.onDestroy()
    }



    // mostrar la información obtenida de la API
    fun mostrarInicio(respuesta: dataInicioResponse) {

        // mostrar el nombre del lector
        val nombre = respuesta.usuario?.nombre ?: "Lector"
        txtSaludo.text = "¡Hola, $nombre!"

        // obtener el prestamo actual
        val prestamo = respuesta.prestamoActual

        if (prestamo != null) {

            //si no existe se oculta la tarjeta
            cardPrestamoActual.visibility = View.VISIBLE
            txtSinPrestamos.visibility = View.GONE

            txtTituloLibro.text = prestamo.titulo
            txtAutorLibro.text = prestamo.autores
            txtFechaLimite.text = "Fecha límite: ${prestamo.fechaLimiteDevolucion}"
            txtEstadoPrestamo.text = prestamo.estadoPrestamo


            // obtener la ruta de la portada desde la aapi
            val rutaPortada = prestamo.rutaPortada?.trim().orEmpty()

            // dirección del servidor donde estan las img
            val urlBase = "https://christianjsh.somos4a.org/bibliodesk_apis/"

            // obtener la direccion completa de la imagen
            val urlPortada = if (rutaPortada.startsWith("http")) {
                rutaPortada
            } else if (rutaPortada.isNotEmpty()) {
                urlBase + rutaPortada.trimStart('/')
            } else {
                null
            }

            // mostrar la portada con glide
            Glide.with(this)
                .load(urlPortada)
                .into(imgPortadaPrestamo)


        } else {

            cardPrestamoActual.visibility = View.GONE
            txtSinPrestamos.visibility = View.VISIBLE
        }
    }


    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
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


}