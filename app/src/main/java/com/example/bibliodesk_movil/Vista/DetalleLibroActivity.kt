package com.example.bibliodesk_movil.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bibliodesk_movil.R
import com.example.bibliodesk_movil.Red.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val baseUrl = "http://192.168.100.53/bibliodesk_apis/" // ✅ Tu URL

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

        val idLibro = intent.getIntExtra("id_libro", 0)
        if (idLibro > 0) {
            cargarDetalle(idLibro)
        } else {
            Toast.makeText(this, "Libro no válido", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnRegresar.setOnClickListener { finish() }
        btnLeerTexto.setOnClickListener {
            Toast.makeText(this, "Función próximamente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarDetalle(idLibro: Int) {
        lifecycleScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    RetrofitCliente.apiService.obtenerDetalleLibro(idLibro)
                }

                if (res.exito && res.datos != null) {
                    val lib = res.datos
                    txtTitulo.text = lib.titulo
                    txtAutor.text = lib.autor
                    txtCategoria.text = lib.categoria
                    txtDescripcion.text = lib.descripcion ?: ""
                    txtIsbn.text = "ISBN: ${lib.isbn ?: "N/A"}"
                    txtEditorial.text = "Editorial: ${lib.editorial}"
                    txtAnio.text = "Año: ${lib.anio ?: "N/A"}"
                    txtPaginas.text = "Páginas: ${lib.paginas ?: "N/A"}"

                    if (lib.ejemplares_disponibles > 0) {
                        btnDisponibilidad.text = "Disponible (${lib.ejemplares_disponibles})"
                        btnDisponibilidad.setBackgroundColor(0xFF7B1E1E.toInt())
                    } else {
                        btnDisponibilidad.text = "No disponible"
                        btnDisponibilidad.setBackgroundColor(0xFF9E9E9E.toInt())
                    }

                    if (!lib.ruta_portada.isNullOrEmpty()) {
                        Glide.with(this@DetalleLibroActivity)
                            .load(baseUrl + lib.ruta_portada)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .error(android.R.drawable.stat_notify_error)
                            .into(ivPortada)
                    }
                } else {
                    Toast.makeText(this@DetalleLibroActivity,
                        res.mensaje ?: "Error al cargar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DetalleLibroActivity,
                    "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
