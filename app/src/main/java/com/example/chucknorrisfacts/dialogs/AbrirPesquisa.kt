package com.example.chucknorrisfacts.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.chucknorrisfacts.R

class AbrirPesquisa : DialogFragment() {

    var txtCampoPesquisa: EditText? = null
    var btPesquisar: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = activity!!.layoutInflater.inflate(R.layout.dlg_pesquisa, null)

        txtCampoPesquisa = view.findViewById(R.id.campo_pesquisa)
        btPesquisar = view.findViewById(R.id.btn_pesquisar)

        txtCampoPesquisa!!.requestFocus()

        btPesquisar!!.setOnClickListener {

            //Verificando se o campo de pesquisa foi preenchido
            if (txtCampoPesquisa!!.text.isEmpty()) {
                txtCampoPesquisa!!.error = getString(R.string.dlg_pesquisar_campo_vazio)
            } else {

                //Retornando palavra chave
                (activity as RetornaQueryPesquisa).aoDefinirPesquisa(txtCampoPesquisa!!.text.toString())
                dismiss()
            }
        }

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        return alert.create()
    }


    interface RetornaQueryPesquisa {

        fun aoDefinirPesquisa(query: String) //Retornando a palavra definida para pesquisa no Dialog
    }
}