package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliodesk_movil.Presentador.clsLoginPresentador
import com.example.bibliodesk_movil.R
import java.util.Locale

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: clsLoginPresentador
    private lateinit var txtMatriculaLogin: EditText
    private lateinit var txtContrasenaLogin: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnCrearCuenta : Button

    //viriables para accesibilidad
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

        btnCrearCuenta.setOnClickListener{
            startActivity(Intent(this,RegistroActivity::class.java))
        }

        configurarAccesibilidad()
    }
    //esto lo agregue para que limpie los campos , del login al volver si ya los pususte
    override fun onResume() {
        super.onResume()
        txtMatriculaLogin.text.clear()
        txtContrasenaLogin.text.clear()
    }

    fun ejecutarInicioSesion(v: View){
        val usuario = txtMatriculaLogin.text.toString().trim()
        val clave = txtContrasenaLogin.text.toString().trim()
        presenter.realizarLogin(usuario,clave)
    }

    fun loginExitoso(mensaje: String, nombreCliente: String, idUsuario: Int){
        val preferencias = getSharedPreferences("Bibliodesk", MODE_PRIVATE)
        preferencias.edit().putInt("id_usuario", idUsuario).apply()
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id_usuario",idUsuario)
        startActivity(intent)
        finish()

        if(vozActivada){
            lectorVoz?.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "error_login")
        }
    }

    fun mostrarError(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        if(vozActivada){
            lectorVoz?.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "error_login")
        }
    }

    private fun configurarAccesibilidad(){
        val btnAumentar = findViewById<View>(R.id.btnAumentarTexto)
        val btnDisminuir = findViewById<View>(R.id.btnDisminuirTexto)
        val btnVoz = findViewById<View>(R.id.btnLecturaVoz)

        val tituloLogin = findViewById<TextView>(R.id.txtTituloLogin)
        val subtituloLogin = findViewById<TextView>(R.id.txtSubtituloLogin)
        val etiquetaMatricula = findViewById<TextView>(R.id.txtEtiquetaMatricula)
        val estiquetaContrasenia = findViewById<TextView>(R.id.txtEtiquetaContrasena)
        val eticrearCuenta = findViewById<TextView>(R.id.etiCrearCuenta)


        //agregar los textView o botones donde texto se quiere ajustar
        val textos = listOf<TextView>(
            tituloLogin, subtituloLogin, etiquetaMatricula,estiquetaContrasenia, eticrearCuenta,
            txtMatriculaLogin,
            txtContrasenaLogin,
            btnIniciarSesion,
            btnCrearCuenta
        )

        val tamaniosOriginales = textos .map{it.textSize}

        fun aplicarTamanio(){
            val factor = 1f + nivelTexto * 0.10f

            textos.forEachIndexed{indice , vista ->
                vista.setTextSize( TypedValue.COMPLEX_UNIT_PX, tamaniosOriginales[indice] * factor)
            }
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
                lectorVoz?.speak("Voz activada. Bienvenido a BiblioDesk. Ingresa su matrícula y contraseña."
                        + "Después, presiona el botón acceder a cuenta." + "Si no eres usuario, Crea una nueva cuenta.",
                    TextToSpeech.QUEUE_FLUSH, null, "voz_activada")
            } else {
                btnVoz.contentDescription = "Activar lectura en voz alta"
                lectorVoz?.stop()
            }
        }
        txtMatriculaLogin.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus && vozActivada){
                lectorVoz?.speak("Ingresa su matrícula",
                    TextToSpeech.QUEUE_FLUSH, null,"ayuda_matricula"
                )
            }
        }

        txtContrasenaLogin.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus && vozActivada){
                lectorVoz?.speak("Ingresa su contraseña",
                    TextToSpeech.QUEUE_FLUSH, null,"ayuda_contrasena"
                )
            }
        }

    }

    /*//detener la voz al salir de la pantalla
    override fun onStop(){
        lectorVoz?.stop()
        super.onStop()
    }

    override fun onDestroy(){
        lectorVoz?.shutdown()
        super.onDestroy()
    }*/


}