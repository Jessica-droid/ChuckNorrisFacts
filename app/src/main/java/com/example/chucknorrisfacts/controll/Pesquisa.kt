package com.example.chucknorrisfacts.controll

import com.example.chucknorrisfacts.model.ResultadoPesquisa
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Pesquisa {

    @GET("search")
    fun pesquisarFato(@Query("query") query : String) : Observable<ResultadoPesquisa>
}