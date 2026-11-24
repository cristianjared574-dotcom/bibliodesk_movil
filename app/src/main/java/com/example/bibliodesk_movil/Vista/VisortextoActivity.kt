package com.example.bibliodesk_movil.Vista

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

        val tituloLibro = intent.getStringExtra("titulo_libro") ?: "Lectura de libro"
        txtTituloLibroVisor.text = tituloLibro


        presentador = clsVisorTextoPresentador(this)

        configurarListeners()
        presentador.iniciar()
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

    }

    fun mostrarSeccion(
        seccion: dataContenidoLibro,
        posicionActual: Int,
        totalSecciones: Int,
        hayAnterior: Boolean,
        haySiguiente: Boolean
    ) {
        txtCapituloVisor.text = "Capitulo ${seccion.orden}"
        txtTituloLibroVisor.text = seccion.tituloLibro
        txtSubcapituloVisor.text = seccion.tituloSeccion
        txtContenidoVisor.text = seccion.contenido
        txtContenidoVisor.textSize = tamanioTextoSp
        txtPaginaVisor.text = "Sección $posicionActual de $totalSecciones"
        btnAnteriorPagina.isEnabled = hayAnterior
        btnSiguientePagina.isEnabled = haySiguiente
    }

    fun mostrarSinContenido() {
        txtCapituloVisor.text = "Sin contenido"
        txtSubcapituloVisor.text = ""
        txtContenidoVisor.text = "Este libro aún no tiene secciones registradas."
        txtPaginaVisor.text = "0 de 0"
        btnAnteriorPagina.isEnabled = false
        btnSiguientePagina.isEnabled = false
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

}