package com.example.chucknorrisfacts.controll

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisfacts.adapter.ChuckFactAdapter
import com.example.chucknorrisfacts.model.ResultadoPesquisa
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ChuckFactControll (
    private val context : Context,
    private val tag : String,
    private val baseUrl : String,
    private val compoDisposable : CompositeDisposable,
    private val rvLista : RecyclerView,
    private val noResult_layout : View,
    private val query : String) {


    fun retornaResultadoPesquisa() {

        //Passando a Url principal para o Retrofit
        val requestInterface = Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()) // Definindo o conversor de Json
            .build()
            .create(Pesquisa::class.java) //Definindo a interface que retornará as informaçoes

        compoDisposable.add(
            requestInterface.pesquisarFato(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::tratarResposta,this::tratarErro)
        )
    }

    private fun tratarResposta(chuckFatcs: ResultadoPesquisa){

        //Verificando se houve retorno
        if (chuckFatcs.result.isEmpty()) semDados()
        else exibirDados(chuckFatcs)

    }


    //Preenchendo lista com os dados retornados
    private fun exibirDados(chuckFatcs: ResultadoPesquisa){

        // rvLista.visibility = View.VISIBLE
        noResult_layout.visibility = View.GONE

        //Após carregar os fatos, relacionar o adapter com a lista
        rvLista.adapter = ChuckFactAdapter(chuckFatcs.result, context)
    }

    //Exibir um dialogo
    private fun semDados(){


    }

    //Exibir dialogo
    private fun tratarErro(error: Throwable){
        Log.d(tag, error.message.toString())
    }


}