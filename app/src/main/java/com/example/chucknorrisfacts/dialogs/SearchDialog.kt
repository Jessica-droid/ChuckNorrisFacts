package com.example.chucknorrisfacts.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.chucknorrisfacts.R
import kotlinx.android.synthetic.main.dlg_pesquisa.view.*

class SearchDialog(private val context: Context) {

    private val view by lazy { LayoutInflater.from(context).inflate(R.layout.dlg_pesquisa, null) }
    private val dialog by lazy { AlertDialog.Builder(context).setView(view).create() }


     fun show(onConfirmation: (String) -> Unit) {

        view.btn_pesquisar.setOnClickListener {
            if (view.campo_pesquisa.text.toString().isNotEmpty()) {
                onConfirmation(view.campo_pesquisa.text.toString())
                dialog.dismiss()
            } else {
                view.campo_pesquisa.error = context.getString(R.string.lb_required_field_message)
            }

        }

         dialog.show()

    }


}