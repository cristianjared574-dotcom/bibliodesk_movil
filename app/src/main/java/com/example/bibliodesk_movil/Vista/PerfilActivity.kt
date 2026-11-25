package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliodesk_movil.Modelo.dataPerfil
import com.example.bibliodesk_movil.Presentador.clsPerfilPresentador
import com.example.bibliodesk_movil.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class PerfilActivity : AppCompatActivity() {

    private lateinit var btnRegresar: ImageButton
    private lateinit var txtNombreCompleto: TextView
    private lateinit var txtRol: TextView
    private lateinit var txtMatricula: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var btnCerrarSesion: Button

    private lateinit var presenter: clsPerfilPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        btnRegresar = findViewById(R.id.btnRegresar)
        txtNombreCompleto = findViewById(R.id.txtNombreCompleto)
        txtRol = findViewById(R.id.txtRol)
        txtMatricula = findViewById(R.id.txtMatricula)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        presenter = clsPerfilPresentador(this)

        val preferencias = getSharedPreferences("Bibliodesk", MODE_PRIVATE)
        val idUsuario = preferencias.getInt("id_usuario", -1)

        presenter.obtenerPerfil(idUsuario)

        configurarMenu()

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, CerrarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    fun mostrarPerfil(datos: dataPerfil){
        txtNombreCompleto.text = datos.nombre_completo
        txtMatricula.text = datos.matricula
        txtCorreo.text = datos.correo
        txtTelefono.text = datos.telefono ?: "No registrado"
        txtRol.text = datos.tipo_usuario
    }

    fun mostrarError(mensaje: String){
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show()
    }

    private fun configurarMenu(){
        val menu = findViewById<BottomNavigationView>(R.id.menuInferior)

        //cuando abre esta pantalla, se marca catalogo
        menu.selectedItemId = R.id.nav_perfil

        menu.setOnItemSelectedListener { opcion ->
            when(opcion.itemId){


                R.id.nav_inicio->{

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_catalogo -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                    true
                }

                R.id.nav_prestamos -> {
                    startActivity(Intent(this, HistorialprestamoActivity::class.java))
                    false
                }

                R.id.nav_perfil ->{
                    true
                }

                else -> false
            }

        }
    }
}


