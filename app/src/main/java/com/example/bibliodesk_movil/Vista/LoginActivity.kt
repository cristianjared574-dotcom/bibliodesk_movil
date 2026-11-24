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
import com.example.bibliodesk_movil.Presentador.clsLoginPresentador
import com.example.bibliodesk_movil.R

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: clsLoginPresentador
    private lateinit var txtMatriculaLogin: EditText
    private lateinit var txtContrasenaLogin: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnCrearCuenta : Button

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

        //inicializar presentador
        presenter = clsLoginPresentador(this)

        //listener del boton
        btnIniciarSesion.setOnClickListener(this::ejecutarInicioSesion)
    }
    //esto lo agregue para que limpie los campos , del login al volver si ya los pususte
    // ala hota de concetarlos ya n lo aceptes es lo unico que movi lo demas no tte ko toque si llega a aparecer que movi tu docuemntoo
    override fun onResume() {
        super.onResume()
        txtMatriculaLogin.text.clear()
        txtContrasenaLogin.text.clear()
    }

    fun ejecutarInicioSesion(v: View){
        val usuario = txtMatriculaLogin.text.toString().toString().trim()
        val clave = txtContrasenaLogin.text.toString().trim()
        presenter.realizarLogin(usuario,clave)
    }

    fun loginExitoso(mensaje: String, nombreCliente: String){
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}