package com.example.chucknorrisfacts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.domain.model.ChuckFact
import kotlinx.android.synthetic.main.item_lista.view.*
import java.util.*

class ChuckFactAdapter(private val items: List<ChuckFact>, private val context: Context) :
    RecyclerView.Adapter<ChuckFactAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.let {
            it.bindView(items[position])
            it.itemView.setOnClickListener {

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, items[position].url)
                context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.lb_share_content)))

            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        //Preenchendo as informações
        fun bindView(fato: ChuckFact) {

            itemView.item_descricao.text = fato.value
            itemView.item_descricao.textSize = defineTamanhoFonte()
            itemView.item_categoria.text = defineCategoria(fato.categories)

        }

        //Verificando se a categoria foi definida, se não foi, retorna a label UNCATEGORIZED
        private fun defineCategoria(categoria: Array<String>): String {

            return if (categoria.isEmpty()) {
                "UNCATEGORIZED"
            } else {
                categoria[0].toUpperCase(Locale.getDefault())
            }
        }

        //Verificando o tamanho da String para selecionar o tamanho de fonte apropriado
        private fun defineTamanhoFonte(): Float {

            return if (itemView.item_categoria.text.length >= 80) 20f
            else 24f

        }




    }

}