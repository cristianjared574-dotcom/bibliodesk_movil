package com.example.bibliodesk_movil.Vista

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

class HistorialprestamoActivity : AppCompatActivity() {

    private lateinit var presentador: clsHistorialPrestamoPresentador
    private lateinit var spinnerLibros: Spinner
    private lateinit var RecycleLibros: RecyclerView
    private lateinit var AdaptadorHistorialPrestamo: HistorialPrestamoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historialprestamo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        spinnerLibros = findViewById(R.id.spnestado)
        RecycleLibros = findViewById(R.id.rvCursos)

        RecycleLibros.layoutManager = LinearLayoutManager(this)
        AdaptadorHistorialPrestamo = HistorialPrestamoAdaptador(emptyList())
        RecycleLibros.adapter = AdaptadorHistorialPrestamo

        presentador = clsHistorialPrestamoPresentador(this)
        configurarListeners()
        presentador.iniciar()
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
}