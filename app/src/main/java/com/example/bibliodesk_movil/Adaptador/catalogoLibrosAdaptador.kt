package com.example.bibliodesk_movil.Adaptador

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bibliodesk_movil.Modelo.dataCatalogoLibros
import com.example.bibliodesk_movil.R
import com.example.bibliodesk_movil.Vista.CatalogoActivity

class catalogoLibrosAdaptador (private val activity: CatalogoActivity, private var listaCatalogoLibros: List<dataCatalogoLibros>): RecyclerView.Adapter<catalogoLibrosAdaptador.LibrosViewHolder>(){
    //Accesibilidad
    private var factorTexto = 1f

    class LibrosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTituloLibro: TextView = itemView.findViewById(R.id.txtTitulo)
        val tvNombreAutor: TextView = itemView.findViewById(R.id.txtAutor)
        val tvdisponible: TextView = itemView.findViewById(R.id.txtDisponibilidad)
        val portadaLibro: ImageView = itemView.findViewById(R.id.imglibro)
        val imgDisponible: ImageView = itemView.findViewById(R.id.imgDisponibilidad)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)

        //variables para accesibilidad
        val tamanioTitulo = tvTituloLibro.textSize
        val tamanioAutor = tvNombreAutor.textSize
        val tamanioDisponible = tvdisponible.textSize
        val tamanioBoton = btnVerDetalle.textSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro, parent,false)
        return LibrosViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibrosViewHolder, position: Int){
        val catalogo = listaCatalogoLibros[position]
        holder.tvTituloLibro.text = catalogo.titulo
        holder.tvNombreAutor.text = catalogo.autor
        holder.tvdisponible.text = if (catalogo.ejemplaresDisponibles > 0) {
            holder.imgDisponible.setImageResource(R.drawable.ic_disponible_libro)
            "Disponible"
        } else {
            holder.imgDisponible.setImageResource(R.drawable.ic_nodisponible_libro)
            "No disponible"
        }
        val baseUrl = "http://10.73.125.235/bibliodesk_web_movil/"
        val urlImagen = baseUrl + catalogo.rutaPortada
        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.portadaLibro)

        holder.btnVerDetalle.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                activity.abrirDetalle(catalogo)
            }
        })

        holder.itemView.setOnClickListener {
            activity.leerLibro(catalogo)
        }

        //Accesibilidad
        holder.tvTituloLibro.setTextSize(TypedValue.COMPLEX_UNIT_PX, holder.tamanioTitulo * factorTexto)
        holder.tvNombreAutor.setTextSize(TypedValue.COMPLEX_UNIT_PX, holder.tamanioAutor * factorTexto)
        holder.tvdisponible.setTextSize(TypedValue.COMPLEX_UNIT_PX, holder.tamanioDisponible * factorTexto)
        holder.btnVerDetalle.setTextSize(TypedValue.COMPLEX_UNIT_PX, holder.tamanioBoton * factorTexto)
    }

    override fun getItemCount(): Int{
        return listaCatalogoLibros.size
    }

    fun actualizarLista(nuevaLista: List<dataCatalogoLibros>){
        listaCatalogoLibros = nuevaLista
        notifyDataSetChanged()
    }


    //Accesibilidad
    fun cambiarTamanioTexto(factor: Float){
        factorTexto = factor
        notifyDataSetChanged()
    }
}