package com.example.chucknorrisfacts.services

import com.example.chucknorrisfacts.domain.model.SearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchApi {

    @GET("search")
    fun searchWith(@Query("query") query : String) : Observable<SearchResult>
}