package com.example.bibliodesk_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bibliodesk_movil.Modelo.dataPrestamoHistorial
import com.example.bibliodesk_movil.R

class HistorialPrestamoAdaptador(
    private var listaPrestamos: List<dataPrestamoHistorial>
) : RecyclerView.Adapter<HistorialPrestamoAdaptador.PrestamoViewHolder>() {

    class PrestamoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLibro: ImageView = itemView.findViewById(R.id.imglibro)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtAutor: TextView = itemView.findViewById(R.id.txtAutor)
        val txtPrestamo: TextView = itemView.findViewById(R.id.txtPrestamo)
        val txtDevolucion: TextView = itemView.findViewById(R.id.txtDevolucion)
        val txtActividad: TextView = itemView.findViewById(R.id.txtActividad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historialprestamo, parent, false)
        return PrestamoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrestamoViewHolder, position: Int) {
        val item = listaPrestamos[position]
        val context = holder.itemView.context

        holder.txtTitulo.text = item.nombreLibro
        holder.txtAutor.text = item.nombreAutor.ifBlank { "Sin autor" }
        holder.txtPrestamo.text = "Préstamo: ${item.fechaPrestamo}"
        holder.txtDevolucion.text = "Devolución: ${item.fechaDevolucion}"
        holder.txtActividad.text = item.estado

        val baseUrl = "https://christianjsh.somos4a.org/bibliodesk_apis/"
        val urlImagen = if (item.rutaImagen.startsWith("http")) {
            item.rutaImagen
        } else {
            baseUrl + item.rutaImagen.removePrefix("/")
        }

        Glide.with(context)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .centerCrop()
            .into(holder.imgLibro)
    }

    override fun getItemCount(): Int = listaPrestamos.size

    fun actualizarLista(nuevaLista: List<dataPrestamoHistorial>) {
        listaPrestamos = nuevaLista
        notifyDataSetChanged()
    }
}