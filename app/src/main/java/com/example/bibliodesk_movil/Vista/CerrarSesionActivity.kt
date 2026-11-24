package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliodesk_movil.Modelo.SesionUsuario
import com.example.bibliodesk_movil.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CerrarSesionActivity : AppCompatActivity() {

    private lateinit var btnRegresar: ImageButton
    private lateinit var btnConfirmar: Button
    private lateinit var btnCancelar: Button
    private lateinit var menuInferior: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerrar_sesion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Conectar los elementos de la vista
        btnRegresar = findViewById(R.id.btnRegresar)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnCancelar = findViewById(R.id.btnCancelar)
        menuInferior = findViewById(R.id.menuInferior)

        // Marcar Perfil seleccionado en el menú inferior
        menuInferior.selectedItemId = R.id.nav_perfil

        // botonn Regresar vuelve atrás sin hacer nada
        btnRegresar.setOnClickListener {
            finish()
        }

        // btoon Confirmar cierra sesión y vuelve al inicio de sesión
        btnConfirmar.setOnClickListener {
            // borra los datos del usuario actual
            SesionUsuario.cerrarSesion()

            // aqui rregresa a la pantalla de Login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        // el botón Cancelar vuelve a Perfil sin cerrar sesión
        btnCancelar.setOnClickListener {
            finish()
        }

    }
}
