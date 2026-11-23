package com.example.bibliodesk_movil.Vista

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bibliodesk_movil.Modelo.dataInicioResponse
import com.example.bibliodesk_movil.Presentador.clsInicioPresentador
import com.example.bibliodesk_movil.R

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: clsInicioPresentador

    private lateinit var txtSaludo: TextView
    private lateinit var txtTituloLibro: TextView
    private lateinit var txtAutorLibro: TextView
    private lateinit var txtFechaLimite: TextView
    private lateinit var txtEstadoPrestamo: TextView
    private lateinit var imgPortadaPrestamo: ImageView
    private lateinit var cardPrestamoActual: View
    private lateinit var txtSinPrestamos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtSaludo = findViewById(R.id.txtSaludo)
        txtTituloLibro = findViewById(R.id.txtTituloLibro)
        txtAutorLibro = findViewById(R.id.txtAutorLibro)
        txtFechaLimite = findViewById(R.id.txtFechaLimite)
        txtEstadoPrestamo = findViewById(R.id.txtEstadoPrestamo)
        imgPortadaPrestamo = findViewById(R.id.imgPortadaPrestamo)
        cardPrestamoActual = findViewById(R.id.cardPrestamoActual)
        txtSinPrestamos = findViewById(R.id.txtSinPrestamos)

        presenter = clsInicioPresentador(this)

        // obtener el id del usuario enviado desde Login
        val idUsuario = intent.getIntExtra("id_usuario", -1)

        if (idUsuario > 0) {
            presenter.obtenerInicio(idUsuario)
        } else {
            txtSaludo.text = "¡Bienvenido a BiblioDesk!"
            cardPrestamoActual.visibility = View.GONE
            txtSinPrestamos.visibility = View.GONE
        }
    }

    // mostrar la información obtenida de la API
    fun mostrarInicio(respuesta: dataInicioResponse) {

        // mostrar el nombre del lector
        val nombre = respuesta.usuario?.nombre ?: "Lector"
        txtSaludo.text = "¡Hola, $nombre!"

        // obtener el prestamo actual
        val prestamo = respuesta.prestamoActual

        if (prestamo != null) {

            //si no existe se oculta la tarjeta
            cardPrestamoActual.visibility = View.VISIBLE
            txtSinPrestamos.visibility = View.GONE

            txtTituloLibro.text = prestamo.titulo
            txtAutorLibro.text = prestamo.autores
            txtFechaLimite.text = "Fecha límite: ${prestamo.fechaLimiteDevolucion}"
            txtEstadoPrestamo.text = prestamo.estadoPrestamo


            // obtener la ruta de la portada desde la aapi
            val rutaPortada = prestamo.rutaPortada?.trim().orEmpty()

            // dirección del servidor donde estan las img
            val urlBase = "https://christianjsh.somos4a.org/bibliodesk_apis/"

            // obtener la direccion completa de la imagen
            val urlPortada = if (rutaPortada.startsWith("http")) {
                rutaPortada
            } else if (rutaPortada.isNotEmpty()) {
                urlBase + rutaPortada.trimStart('/')
            } else {
                null
            }

            // mostrar la portada con glide
            Glide.with(this)
                .load(urlPortada)
                .into(imgPortadaPrestamo)


        } else {

            cardPrestamoActual.visibility = View.GONE
            txtSinPrestamos.visibility = View.VISIBLE
        }
    }


    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }



}