package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.widget.TextView
import java.util.Locale
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliodesk_movil.Presentador.clsLoginPresentador
import com.example.bibliodesk_movil.R

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: clsLoginPresentador
    private lateinit var txtMatriculaLogin: EditText
    private lateinit var txtContrasenaLogin: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnCrearCuenta: Button
    private var nivelTexto = 0
    private var lectorVoz: TextToSpeech? = null
    private var vozActivada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtMatriculaLogin = findViewById(R.id.txtMatriculaLogin)
        txtContrasenaLogin = findViewById(R.id.txtContrasenaLogin)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)

        //inicializar presentador
        presenter = clsLoginPresentador(this)

        //listener del boton
        btnIniciarSesion.setOnClickListener(this::ejecutarInicioSesion)
        btnCrearCuenta.setOnClickListener(this::abrirRegistro)
        configurarAccesibilidad()
    }

    fun ejecutarInicioSesion(v: View){
        val usuario = txtMatriculaLogin.text.toString().toString().trim()
        val clave = txtContrasenaLogin.text.toString().trim()
        presenter.realizarLogin(usuario,clave)
    }

    fun abrirRegistro(v: View) {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }


    fun loginExitoso(mensaje: String, idUsuario: Int) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

        // abrir Inicio y enviar el ID del lector
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)

        // lectura en voz alta
        if (vozActivada) {
            lectorVoz?.speak(
                mensaje,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "error_login"
            )
        }

        finish()
    }

    fun mostrarError(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }


    private fun configurarAccesibilidad() {

        val btnAumentar = findViewById<View>(R.id.btnAumentarTexto)
        val btnDisminuir = findViewById<View>(R.id.btnDisminuirTexto)
        val btnVoz = findViewById<View>(R.id.btnLecturaVoz)

        val tituloLogin = findViewById<TextView>(R.id.txtTituloLogin)
        val subtituloLogin = findViewById<TextView>(R.id.txtSubtituloLogin)
        val etiquetaMatricula = findViewById<TextView>(R.id.txtEtiquetaMatricula)
        val etiquetaContrasena = findViewById<TextView>(R.id.txtEtiquetaContrasena)
        val crearCuenta = findViewById<TextView>(R.id.textView)

        // Componentes cuyo texto puede aumentar
        val textos = listOf<TextView>(
            tituloLogin,
            subtituloLogin,
            etiquetaMatricula,
            etiquetaContrasena,
            crearCuenta,
            txtMatriculaLogin,
            txtContrasenaLogin,
            btnIniciarSesion,
            btnCrearCuenta
        )

        val tamaniosOriginales = textos.map { it.textSize }

        fun aplicarTamanio() {
            val factor = 1f + nivelTexto * 0.10f

            textos.forEachIndexed { indice, vista ->
                vista.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    tamaniosOriginales[indice] * factor
                )
            }

            btnAumentar.isEnabled = nivelTexto < 5
            btnDisminuir.isEnabled = nivelTexto > 0
        }

        btnAumentar.setOnClickListener {
            nivelTexto++
            aplicarTamanio()
        }

        btnDisminuir.setOnClickListener {
            nivelTexto--
            aplicarTamanio()
        }

        aplicarTamanio()

        // Configurar lectura en voz alta
        btnVoz.isEnabled = false

        lectorVoz = TextToSpeech(this) { estado ->
            if (estado == TextToSpeech.SUCCESS) {
                val idioma = lectorVoz?.setLanguage(Locale("es", "MX"))
                btnVoz.isEnabled = idioma != null && idioma >= 0
            }
        }

        btnVoz.setOnClickListener {
            vozActivada = !vozActivada

            if (vozActivada) {
                lectorVoz?.speak(
                    "Bienvenido a BiblioDesk. Ingresa tu matrícula y contraseña.",
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "voz_login"
                )
            } else {
                lectorVoz?.stop()
            }
        }

        // Leer instrucciones al seleccionar los campos
        txtMatriculaLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && vozActivada) {
                lectorVoz?.speak(
                    "Ingresa tu matrícula",
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "matricula"
                )
            }
        }

        txtContrasenaLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && vozActivada) {
                lectorVoz?.speak(
                    "Ingresa tu contraseña",
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "contrasena"
                )
            }
        }
    }

    override fun onDestroy() {
        lectorVoz?.stop()
        lectorVoz?.shutdown()
        super.onDestroy()
    }

}