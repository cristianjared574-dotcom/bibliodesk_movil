package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliodesk_movil.Presentador.clsRegistroPresentador
import com.example.bibliodesk_movil.R
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.widget.TextView
import java.util.Locale


class RegistroActivity : AppCompatActivity() {

    private lateinit var presenter: clsRegistroPresentador

    private lateinit var edtMatricula: EditText
    private lateinit var edtNombre: EditText
    private lateinit var edtApellidoPaterno: EditText
    private lateinit var edtApellidoMaterno: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtTelefono: EditText
    private lateinit var edtContrasena: EditText
    private lateinit var edtConfirmarContrasena: EditText
    private lateinit var btnCrearCuenta: Button
    private var nivelTexto = 0
    private var lectorVoz: TextToSpeech? = null
    private var vozActivada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtMatricula = findViewById(R.id.edtMatricula)
        edtNombre = findViewById(R.id.edtNombre)
        edtApellidoPaterno = findViewById(R.id.edtApellidoPaterno)
        edtApellidoMaterno = findViewById(R.id.edtApellidoMaterno)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtTelefono = findViewById(R.id.edtTelefono)
        edtContrasena = findViewById(R.id.edtContrasena)
        edtConfirmarContrasena = findViewById(R.id.edtConfirmarContrasena)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)

        presenter = clsRegistroPresentador(this)

        btnCrearCuenta.setOnClickListener(this::ejecutarRegistro)



        val btnRegresar = findViewById<View>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            finish()
        }

        configurarAccesibilidad()

    }

    fun ejecutarRegistro(v: View) {

        val matricula = edtMatricula.text.toString().trim()
        val nombre = edtNombre.text.toString().trim()
        val apellidoPaterno = edtApellidoPaterno.text.toString().trim()
        val apellidoMaterno = edtApellidoMaterno.text.toString().trim()
        val correo = edtCorreo.text.toString().trim()
        val telefono = edtTelefono.text.toString().trim()
        val contrasena = edtContrasena.text.toString()
        val confirmarContrasena = edtConfirmarContrasena.text.toString()

        presenter.registrarUsuario(matricula, nombre, apellidoPaterno, apellidoMaterno,
            correo, telefono, contrasena, confirmarContrasena
        )
    }


    private fun configurarAccesibilidad() {

        // Obtener los botones de accesibilidad
        val btnAumentar = findViewById<View>(R.id.btnAumentarTexto)
        val btnDisminuir = findViewById<View>(R.id.btnDisminuirTexto)
        val btnVoz = findViewById<View>(R.id.btnLectura)

        // Textos que cambiarán de tamaño
        val textos = listOf<TextView>(
            edtMatricula,
            edtNombre,
            edtApellidoPaterno,
            edtApellidoMaterno,
            edtCorreo,
            edtTelefono,
            edtContrasena,
            edtConfirmarContrasena,
            btnCrearCuenta
        )

        // Guardar los tamaños originales
        val tamaniosOriginales = textos.map { it.textSize }

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

        // Aumentar tamaño
        btnAumentar.setOnClickListener {
            if (nivelTexto < 5) {
                nivelTexto++
                aplicarTamanio()
            }
        }

        // Disminuir tamaño
        btnDisminuir.setOnClickListener {
            if (nivelTexto > 0) {
                nivelTexto--
                aplicarTamanio()
            }
        }

        aplicarTamanio()

        // Preparar el lector de voz
        btnVoz.isEnabled = false
        btnVoz.contentDescription = "Activar lectura en voz alta"

        lectorVoz = TextToSpeech(this) { estado ->

            if (estado == TextToSpeech.SUCCESS) {

                val idioma = lectorVoz?.setLanguage(
                    Locale.forLanguageTag("es-MX")
                )

                btnVoz.isEnabled =
                    idioma != null &&
                            idioma != TextToSpeech.LANG_MISSING_DATA &&
                            idioma != TextToSpeech.LANG_NOT_SUPPORTED
            }
        }

        // Activar o detener lectura
        btnVoz.setOnClickListener {

            if (vozActivada) {

                lectorVoz?.stop()
                vozActivada = false
                btnVoz.isSelected = false
                btnVoz.contentDescription = "Activar lectura en voz alta"

            } else {

                // Instrucciones del formulario
                val contenido =
                    "Crear cuenta en BiblioDesk. " +
                            "Completa los siguientes campos para registrarte. " +
                            "Matrícula. " +
                            "Nombre. " +
                            "Apellido paterno. " +
                            "Apellido materno. " +
                            "Correo electrónico. " +
                            "Teléfono. " +
                            "Crear contraseña. " +
                            "Confirmar contraseña. " +
                            "Al terminar, presiona el botón Crear cuenta."

                // Leer las instrucciones
                lectorVoz?.speak(
                    contenido,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "lectura_registro"
                )

                vozActivada = true
                btnVoz.isSelected = true
                btnVoz.contentDescription = "Desactivar lectura en voz alta"
            }
        }
    }

    override fun onDestroy() {
        lectorVoz?.stop()
        lectorVoz?.shutdown()
        super.onDestroy()
    }


    fun registroExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show() //mensaje temporal como en consola

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

}

