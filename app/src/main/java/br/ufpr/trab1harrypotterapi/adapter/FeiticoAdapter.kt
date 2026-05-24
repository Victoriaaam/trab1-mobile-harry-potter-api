package br.ufpr.trab1harrypotterapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.trab1harrypotterapi.R
import br.ufpr.trab1harrypotterapi.model.Spell

class FeiticoAdapter(
    private val feiticos: List<Spell>,
    private val onClick: (Spell) -> Unit
) : RecyclerView.Adapter<FeiticoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNomeFeitico)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feitico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feitico = feiticos[position]
        holder.tvNome.text = feitico.name
        holder.itemView.setOnClickListener { onClick(feitico) }
    }

    override fun getItemCount() = feiticos.size
}
