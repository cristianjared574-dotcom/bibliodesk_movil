package com.example.bibliodesk_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bibliodesk_movil.Modelo.dataLibroDetalle
import com.example.bibliodesk_movil.Presentador.clsDetalleLibroPresentador
import com.example.bibliodesk_movil.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var btnRegresar: ImageButton
    private lateinit var ivPortada: ImageView
    private lateinit var txtTitulo: TextView
    private lateinit var txtAutor: TextView
    private lateinit var txtCategoria: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtIsbn: TextView
    private lateinit var txtEditorial: TextView
    private lateinit var txtAnio: TextView
    private lateinit var txtPaginas: TextView
    private lateinit var btnLeerTexto: Button
    private lateinit var btnDisponibilidad: Button

    private val baseUrl = "https://christianjsh.somos4a.org/bibliodesk_apis/"
    private lateinit var presentador: clsDetalleLibroPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_libro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        btnRegresar = findViewById(R.id.btnRegresar)
        ivPortada = findViewById(R.id.ivPortada)
        txtTitulo = findViewById(R.id.txtTitulo)
        txtAutor = findViewById(R.id.txtAutor)
        txtCategoria = findViewById(R.id.txtCategoria)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        txtIsbn = findViewById(R.id.txtIsbn)
        txtEditorial = findViewById(R.id.txtEditorial)
        txtAnio = findViewById(R.id.txtAnio)
        txtPaginas = findViewById(R.id.txtPaginas)
        btnLeerTexto = findViewById(R.id.btnLeerTexto)
        btnDisponibilidad = findViewById(R.id.btnDisponibilidad)

        btnRegresar.setOnClickListener { finish() }

        configurarMenu()
        btnLeerTexto.setOnClickListener {
            startActivity(Intent(this, VisortextoActivity::class.java))
        }

        presentador = clsDetalleLibroPresentador(this)
        val idLibro = intent.getIntExtra("id_libro", 0)
        presentador.cargarDetalle(idLibro)
    }

    fun mostrarDetalleLibro(libro: dataLibroDetalle) {
        txtTitulo.text = libro.titulo
        txtAutor.text = libro.autor
        txtCategoria.text = libro.categoria
        txtDescripcion.text = libro.descripcion
        txtEditorial.text = "Editorial: " + libro.editorial
        txtIsbn.text = "ISBN: " + libro.isbn
        txtAnio.text = "Año: " + libro.anio.toString()
        txtPaginas.text = "Páginas: " + libro.paginas.toString()
        btnDisponibilidad.text = if (libro.ejemplares_disponibles > 0) {
            "Solicitar préstamo (${libro.ejemplares_disponibles} disponibles)"
        } else {
            "No disponible"
        }
        btnDisponibilidad.isEnabled = libro.ejemplares_disponibles > 0

        if (libro.ruta_portada.isNotBlank()) {
            val urlPortada = if (libro.ruta_portada.startsWith("http")) {
                libro.ruta_portada
            } else {
                baseUrl + libro.ruta_portada.removePrefix("/")
            }
            Glide.with(this)
                .load(urlPortada)
                .into(ivPortada)
        }
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
    private fun configurarMenu(){
        val menu = findViewById<BottomNavigationView>(R.id.menuInferior)

        //cuando abre esta pantalla, se marca catalogo
        menu.selectedItemId = R.id.nav_catalogo

        menu.setOnItemSelectedListener { opcion ->
            when(opcion.itemId){
                R.id.nav_catalogo -> true

                R.id.nav_inicio->{
                    startActivity(Intent(this, MainActivity::class.java))
                    false
                }

                R.id.nav_prestamos -> {
                    startActivity(Intent(this, HistorialprestamoActivity::class.java))
                    true
                }

                R.id.nav_perfil ->{
                    startActivity(Intent(this, PerfilActivity::class.java))
                    false
                }

                else -> false
            }

        }
    }
}