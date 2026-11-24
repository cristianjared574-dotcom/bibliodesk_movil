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
import androidx.lifecycle.lifecycleScope
import com.example.bibliodesk_movil.Modelo.SesionUsuario
import com.example.bibliodesk_movil.R
import com.example.bibliodesk_movil.Red.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilActivity : AppCompatActivity() {

    private lateinit var btnRegresar: ImageButton
    private lateinit var txtNombreCompleto: TextView
    private lateinit var txtRol: TextView
    private lateinit var txtMatricula: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var btnCerrarSesion: Button

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

        actualizarInterfazSesion()

        if (SesionUsuario.idUsuario > 0) {
            cargarPerfilDesdeAPI(SesionUsuario.idUsuario)
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, CerrarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun actualizarInterfazSesion() {
        txtNombreCompleto.text = SesionUsuario.nombreCompleto
        txtRol.text = SesionUsuario.tipoUsuario
        txtMatricula.text = "Matrícula: ${SesionUsuario.matricula}"
        txtCorreo.text = "Correo: ${SesionUsuario.correo}"
        txtTelefono.text = "Teléfono: ${SesionUsuario.telefono}"
    }

    private fun cargarPerfilDesdeAPI(idUsuario: Int) {
        lifecycleScope.launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    RetrofitCliente.apiService.obtenerPerfil(idUsuario)
                }

                if (respuesta.exito && respuesta.datos != null) {
                    val perfil = respuesta.datos
                    SesionUsuario.nombreCompleto = perfil.nombre_completo
                    SesionUsuario.matricula = perfil.matricula
                    SesionUsuario.correo = perfil.correo
                    SesionUsuario.telefono = perfil.telefono ?: ""
                    SesionUsuario.tipoUsuario = perfil.tipo_usuario

                    actualizarInterfazSesion()
                } else {
                    Toast.makeText(this@PerfilActivity,
                        respuesta.mensaje ?: "No se pudo cargar el perfil",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PerfilActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
