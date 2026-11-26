package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliodesk_movil.Adaptador.HistorialPrestamoAdaptador
import com.example.bibliodesk_movil.Modelo.dataPrestamoHistorial
import com.example.bibliodesk_movil.Presentador.clsHistorialPrestamoPresentador
import com.example.bibliodesk_movil.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HistorialprestamoActivity : AppCompatActivity() {

    private lateinit var presentador: clsHistorialPrestamoPresentador
    private lateinit var spinnerLibros: Spinner
    private lateinit var RecycleLibros: RecyclerView
    private lateinit var AdaptadorHistorialPrestamo: HistorialPrestamoAdaptador
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historialprestamo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val prefs = getSharedPreferences("Bibliodesk", MODE_PRIVATE)
        val idPrefs = prefs.getInt("id_usuario", -1)
        val idIntent = intent.getIntExtra("id_usuario", -1)

        idUsuario = when {
            idPrefs > 0 -> idPrefs
            idIntent > 0 -> idIntent
            else -> 1 // ID de prueba por defecto si no hay sesión
        }

        spinnerLibros = findViewById(R.id.spnestado)
        RecycleLibros = findViewById(R.id.rvCursos)

        RecycleLibros.layoutManager = LinearLayoutManager(this)
        AdaptadorHistorialPrestamo = HistorialPrestamoAdaptador(emptyList())
        RecycleLibros.adapter = AdaptadorHistorialPrestamo

        configurarMenu()
        presentador = clsHistorialPrestamoPresentador(this)
        configurarListeners()

        presentador.iniciar(idUsuario)
    }

    private fun configurarListeners() {
        spinnerLibros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presentador.seleccionarFiltro(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun mostrarEstados(estados: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLibros.adapter = adapter
    }

    fun mostrarHistorial(lista: List<dataPrestamoHistorial>) {
        AdaptadorHistorialPrestamo.actualizarLista(lista)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun configurarMenu() {
        val menu = findViewById<BottomNavigationView>(R.id.menuInferior)

        menu.selectedItemId = R.id.nav_prestamos

        menu.setOnItemSelectedListener { opcion ->
            when (opcion.itemId) {
                R.id.nav_inicio -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id_usuario", idUsuario)
                    startActivity(intent)
                    true
                }

                R.id.nav_catalogo -> {
                    val intent = Intent(this, CatalogoActivity::class.java)
                    intent.putExtra("id_usuario", idUsuario)
                    startActivity(intent)
                    true
                }

                R.id.nav_prestamos -> {
                    true
                }

                R.id.nav_perfil -> {
                    val intent = Intent(this, PerfilActivity::class.java)
                    intent.putExtra("id_usuario", idUsuario)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }
}