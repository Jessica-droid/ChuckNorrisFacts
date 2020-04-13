package com.example.chucknorrisfacts.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.adapter.ChuckFactAdapter
import com.example.chucknorrisfacts.controll.ChuckFactControll
import com.example.chucknorrisfacts.dialogs.AbrirPesquisa
import com.example.chucknorrisfacts.dialogs.AbrirPesquisa.RetornaQueryPesquisa
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RetornaQueryPesquisa {

    private val BASE_URL = "https://api.chucknorris.io/jokes/"
    private val compoDisposable = CompositeDisposable()

    private var broadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent!!.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {

                //Nao há conexao com a internet
                onDisconnect()
            } else {

                //Exibir lista
                onConnect()
            }
        }
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarLista()
    }


    private fun inicializarLista() {
        chuck_facts_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        chuck_facts_list.setHasFixedSize(true)

        //Inicializando a lista vazia
        chuck_facts_list.adapter = ChuckFactAdapter(ArrayList(), this)
    }

     fun pesquisar(query: String) {
        ChuckFactControll(
            this,
            MainActivity::class.java.simpleName,
            BASE_URL,
            compoDisposable,
            chuck_facts_list,
            no_result_layout,
            query
        )
            .retornaResultadoPesquisa()
    }

    private fun onConnect() {
        chuck_facts_list.visibility = View.VISIBLE
        sem_conexao_layout.visibility = View.GONE
    }

    private fun onDisconnect() {
        chuck_facts_list.visibility = View.GONE
        sem_conexao_layout.visibility = View.VISIBLE
    }

    private fun abrirDialogPesquisa() {

        val dialog = AbrirPesquisa()

        dialog.show(supportFragmentManager, "Abrir_Pesquisa")


    }

    override fun aoDefinirPesquisa(query: String) {
        pesquisar(query)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()

        registerReceiver(broadReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(broadReceiver)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_pesquisar -> abrirDialogPesquisa()

        }

        return super.onOptionsItemSelected(item)
    }


}
