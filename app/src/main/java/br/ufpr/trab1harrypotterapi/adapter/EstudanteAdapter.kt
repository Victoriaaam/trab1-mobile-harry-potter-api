package br.ufpr.trab1harrypotterapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.trab1harrypotterapi.R
import br.ufpr.trab1harrypotterapi.model.Character
import coil.load

class EstudanteAdapter(private var estudantes: List<Character>) :
    RecyclerView.Adapter<EstudanteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivEstudante: ImageView = view.findViewById(R.id.ivEstudante)
        val tvNome: TextView = view.findViewById(R.id.tvNomeEstudante)
        val tvCasa: TextView = view.findViewById(R.id.tvCasaEstudante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudante, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudante = estudantes[position]
        holder.tvNome.text = estudante.name
        holder.tvCasa.text = estudante.house ?: "N/A"
        holder.ivEstudante.load(estudante.image) {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.ic_menu_report_image)
        }
    }

    override fun getItemCount() = estudantes.size

    fun updateData(newData: List<Character>) {
        estudantes = newData
        notifyDataSetChanged()
    }
}
