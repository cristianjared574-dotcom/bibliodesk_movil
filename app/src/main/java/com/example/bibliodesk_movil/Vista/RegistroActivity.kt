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

