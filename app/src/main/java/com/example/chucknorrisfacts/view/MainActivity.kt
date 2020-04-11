package com.example.chucknorrisfacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.adapter.ChuckFactAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.chucknorris.io/jokes/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarLista()
    }



    private fun inicializarLista(){
        chuck_facts_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        chuck_facts_list.setHasFixedSize(true)

        //Inicializando a lista vazia
        chuck_facts_list.adapter = ChuckFactAdapter(ArrayList(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.menu_pesquisar -> Log.i("MainActivity","Pesquisa selecinada")

        }

        return super.onOptionsItemSelected(item)
    }
}
