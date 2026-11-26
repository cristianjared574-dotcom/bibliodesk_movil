package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliodesk_movil.Modelo.dataContenidoLibro
import com.example.bibliodesk_movil.Presentador.clsVisorTextoPresentador
import com.example.bibliodesk_movil.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class VisortextoActivity : AppCompatActivity(){

    private lateinit var presentador: clsVisorTextoPresentador

    private lateinit var btnRegresar: ImageButton
    private lateinit var txtSeccion: TextView
    private lateinit var btnAumentarTexto: Button
    private lateinit var btnDisminuirTexto: Button
    private lateinit var btnLecturaVoz: ImageButton

    private lateinit var txtTituloLibroVisor: TextView
    private lateinit var txtCapituloVisor: TextView
    private lateinit var txtSubcapituloVisor: TextView
    private lateinit var txtContenidoVisor: TextView
    private lateinit var txtPaginaVisor: TextView
    private lateinit var btnAnteriorPagina: Button
    private lateinit var btnSiguientePagina: Button

    private var tts: TextToSpeech? = null
    private var tamanioTextoSp = 15f
    private var idLibro: Int = -1
    private var textoActual: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_visortexto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        btnRegresar = findViewById(R.id.btnRegresar)
        txtSeccion = findViewById(R.id.txtSeccion)
        btnAumentarTexto = findViewById(R.id.btnAumentarTexto)
        btnDisminuirTexto = findViewById(R.id.btnDisminuirTexto)
        btnLecturaVoz = findViewById(R.id.btnLecturaVoz)

        txtTituloLibroVisor = findViewById(R.id.txtTituloLibroVisor)
        txtCapituloVisor = findViewById(R.id.txtCapituloVisor)
        txtSubcapituloVisor = findViewById(R.id.txtSubcapituloVisor)
        txtContenidoVisor = findViewById(R.id.txtContenidoVisor)
        txtPaginaVisor = findViewById(R.id.txtPaginaVisor)
        btnAnteriorPagina = findViewById(R.id.btnAnteriorPagina)
        btnSiguientePagina = findViewById(R.id.btnSiguientePagina)


        idLibro = intent.getIntExtra("id_libro",-1)
        txtTituloLibroVisor.text = intent.getStringExtra("titulo_libro") ?: "Lectura de libro"

        configurarMenu()
        configurarVoz()
        presentador = clsVisorTextoPresentador(this)
        configurarListeners()
        presentador.iniciar(idLibro)
    }
    private fun configurarListeners() {
        btnRegresar.setOnClickListener { finish() }

        btnAnteriorPagina.setOnClickListener {
            tts?.stop()
            presentador.irSeccionAnterior()
        }

        btnSiguientePagina.setOnClickListener {
            tts?.stop()
            presentador.irSeccionSiguiente()
        }

        btnAumentarTexto.setOnClickListener {
            if (tamanioTextoSp < 30f) {
                tamanioTextoSp += 2f
                txtContenidoVisor.textSize = tamanioTextoSp
            }
        }

        btnDisminuirTexto.setOnClickListener {
            if (tamanioTextoSp > 12f) {
                tamanioTextoSp -= 2f
                txtContenidoVisor.textSize = tamanioTextoSp
            }
        }

        btnLecturaVoz.setOnClickListener {
            if (tts?.isSpeaking == true) {
                tts?.stop()
            } else if (textoActual.isNotBlank()) {
                tts?.speak(textoActual, TextToSpeech.QUEUE_FLUSH, null, "lectura_seccion")
            }
        }
    }

    private fun configurarVoz() {
        tts = TextToSpeech(this) { estado ->
            if (estado == TextToSpeech.SUCCESS) {
                tts?.setLanguage(Locale("es", "MX"))
            }
        }
    }

    fun mostrarSeccion(
        seccion: dataContenidoLibro,
        posicionActual: Int,
        totalSecciones: Int,
        hayAnterior: Boolean,
        haySiguiente: Boolean
    ) {
        txtCapituloVisor.text = "Capítulo ${seccion.orden}"
        txtTituloLibroVisor.text = seccion.tituloLibro
        txtSubcapituloVisor.text = seccion.tituloSeccion
        txtContenidoVisor.text = seccion.contenido
        txtContenidoVisor.textSize = tamanioTextoSp
        txtPaginaVisor.text = "Sección $posicionActual de $totalSecciones"
        btnAnteriorPagina.isEnabled = hayAnterior
        btnSiguientePagina.isEnabled = haySiguiente
        textoActual = "${seccion.tituloSeccion}. ${seccion.contenido}"
    }

    fun mostrarSinContenido() {
        txtCapituloVisor.text = "Sin contenido"
        txtSubcapituloVisor.text = ""
        txtContenidoVisor.text = "Este libro aún no tiene secciones registradas."
        txtPaginaVisor.text = "0 de 0"
        btnAnteriorPagina.isEnabled = false
        btnSiguientePagina.isEnabled = false
        textoActual = ""
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun configurarMenu() {
        val menu = findViewById<BottomNavigationView>(R.id.menuInferior)
        menu.selectedItemId = R.id.nav_catalogo

        menu.setOnItemSelectedListener { opcion ->
            when (opcion.itemId) {
                R.id.nav_catalogo -> true
                R.id.nav_inicio -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_prestamos -> {
                    startActivity(Intent(this, HistorialprestamoActivity::class.java))
                    true
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onStop() {
        tts?.stop()
        super.onStop()
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }

}